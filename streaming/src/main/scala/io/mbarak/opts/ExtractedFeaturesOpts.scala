package io.mbarak.opts

import io.mbarak.core.domain.UserProfileState
import io.mbarak.showcase._
import org.apache.flink.api.common.accumulators.LongCounter
import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.common.typeinfo.{TypeHint, TypeInformation}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.util.Collector
import org.apache.flink.api.scala._

object ExtractedFeaturesOpts {
  case class JoinedEvents(userId: String, event1: Option[Event], event2: Option[Event2])

  case object EventsJoin extends RichCoFlatMapFunction[Event, Event2, JoinedEvents] {

    var events1Counter: LongCounter = _
    var events2Counter: LongCounter = _

    override def open(parameters: Configuration): Unit = {
      val rt = getRuntimeContext

      events1Counter = rt.getLongCounter("EVENT_1_COUNTER")
      events2Counter = rt.getLongCounter("EVENT_2_COUNTER")
    }

    override def flatMap1(value: Event, out: Collector[JoinedEvents]): Unit = {
      events1Counter.add(1l)
      out.collect(JoinedEvents(value.getUserId, Some(value), None))
    }

    override def flatMap2(value: Event2, out: Collector[JoinedEvents]): Unit = {
      events2Counter.add(1l)
      out.collect(JoinedEvents(value.getUserId, None, Some(value)))
    }
  }

  case object FeatureExtraction extends RichFlatMapFunction[UserProfile, ExtracedFeatures] {
    var featuresCounter: LongCounter = _

    override def open(parameters: Configuration): Unit = {
      featuresCounter = getRuntimeContext.getLongCounter("EXTRACTED_FEATURES")
    }

    override def flatMap(value: UserProfile, out: Collector[ExtracedFeatures]): Unit = {
      extractFeatures(value).foreach(f => {
          out.collect(f)
          featuresCounter.add(1l)
        }
      )
    }

    def extractFeatures(state: UserProfile):  Option[ExtracedFeatures] = for {
      userValue1 <- Option(state.getUserValue1)
      userValue3 <- Option(state.getUserValue3)
      featuresUpdates <- Option(state.getTimestamp)
      (good, neutral, bad) <- Option(state.getUserValue4).map {
        case "good" => (1,0,0)
        case "neutral" => (0,1,0)
        case "bad" => (0,0,1)
      }
    } yield (new ExtracedFeatures(state.getUserId, userValue1, userValue3, featuresUpdates,  good, neutral, bad))
  }

  case object FeaturesJoin extends RichCoFlatMapFunction[JoinedEvents, UserScore, UserProfile] {

    @transient
    var userProfileState: ValueState[UserProfileState] = _

    var scoredProfiles: LongCounter = _

    override def open(parameters: Configuration): Unit = {
      val descriptor: ValueStateDescriptor[UserProfileState] = new ValueStateDescriptor[UserProfileState](
        "user_profiles",
        createTypeInformation[UserProfileState]
      )

      descriptor.setQueryable("user_profiles_query")
      userProfileState = getRuntimeContext.getState(descriptor)
      scoredProfiles = getRuntimeContext.getLongCounter("SCORED_PROFILES")
    }

    override def flatMap1(value: JoinedEvents, out: Collector[UserProfile]): Unit = {
      val profile = if (userProfileState.value() == null) UserProfileState(value.userId, None, None, None, None, None, None) else userProfileState.value()

      val updatedProfile = value.event1.map(e => {
        profile.copy(userValue1 = Some(e.getUserValue1), userValue2 = Some(e.getUserValue2), timeStamp = Some(e.getTimestamp))
        }
      ).getOrElse(profile)


      val newProfile = value.event2.map(e => {
        updatedProfile.copy(userValue3 =  Some(e.getUserValue3), userValue4 = Some(e.getUserValue4), timeStamp = Some(e.getTimestamp))

       }
      ).getOrElse(updatedProfile)

      userProfileState.update(newProfile)

      val res = for {
        userValue1 <- newProfile.userValue1
        userValue2 <- newProfile.userValue2
        userValue3 <- newProfile.userValue3
        userValue4 <- newProfile.userValue4
        timestamp <- newProfile.timeStamp
        score: Int = newProfile.score.getOrElse(-1)
      } yield (new UserProfile(newProfile.userId, userValue1, userValue2, userValue3, userValue4, score, timestamp))

      res.foreach(out.collect)
    }

    override def flatMap2(value: UserScore, out: Collector[UserProfile]): Unit = {
      val profile = if (userProfileState.value() == null) UserProfileState(value.getUserId, None, None, None, None, None, None) else userProfileState.value()

      val newProfile = profile.copy(score = Some(value.getScore))

      scoredProfiles.add(1l)
      userProfileState.update(newProfile)

      val res = for {
        userValue1 <- newProfile.userValue1
        userValue2 <- newProfile.userValue2
        userValue3 <- newProfile.userValue3
        userValue4 <- newProfile.userValue4
        timestamp <- newProfile.timeStamp
        score: Int = newProfile.score.getOrElse(-1)
      } yield (new UserProfile(newProfile.userId, userValue1, userValue2, userValue3, userValue4, score, timestamp))

      res.foreach(out.collect)
    }
  }

  implicit class ExtracedFeaturesOpts(ds: DataStream[ExtracedFeatures]) {
    def addKey(implicit tf: TypeInformation[(String, ExtracedFeatures)]) = ds.map(s => (s.getUserId, s))

  }

  implicit class EventsOpts(ds: DataStream[Event]) {
    def joinWithEvents2(dds: DataStream[Event2]) =
      ds
        .connect(dds)
        .keyBy(_.getUserId, _.getUserId)
        .flatMap(EventsJoin)
  }

  implicit class JoinedEventsOpts(ds: DataStream[JoinedEvents]) {
    def joinWithScores(dds: DataStream[UserScore]) =
      ds
        .connect(dds)
        .keyBy(_.userId, _.getUserId)
        .flatMap(FeaturesJoin)
  }

  implicit class UserProfileOpts(ds: DataStream[UserProfile]) {
    def extractFeatures = ds.flatMap(FeatureExtraction)
    def addKey = ds.map(s => (s.getUserId, s))
  }
}
