package io.mbarak.opts

import io.mbarak.core.domain.UserProfileState

import io.mbarak.showcase._
import org.apache.flink.api.common.accumulators.LongCounter
import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.util.Collector
import org.apache.flink.api.scala._


object ExtractedFeaturesOpts {

  sealed trait UserProfileUpdate {
    def userId: String
  }

  case class Event1UserProfileUpdate(event1: Event) extends UserProfileUpdate {
    override def userId: String = event1.getUserId
  }

  case class Event2UserProfileUpdate(event2: Event2) extends UserProfileUpdate {
    override def userId: String = event2.getUserId
  }

  case class UserScoreUpdate(score: UserScore) extends UserProfileUpdate {
    override def userId: String = score.getUserId
  }

  case object UserProfileBuilder extends RichFlatMapFunction[UserProfileUpdate, UserProfile] {

    var events1Counter: LongCounter = _
    var events2Counter: LongCounter = _
    var scoredProfiles: LongCounter = _

    @transient
    var userProfileState: ValueState[UserProfileState] = _

    override def open(parameters: Configuration): Unit = {
      val rt = getRuntimeContext

      events1Counter = rt.getLongCounter("EVENT_1_COUNTER")
      events2Counter = rt.getLongCounter("EVENT_2_COUNTER")
      scoredProfiles = getRuntimeContext.getLongCounter("SCORED_PROFILES")

      val descriptor: ValueStateDescriptor[UserProfileState] = new ValueStateDescriptor[UserProfileState](
        "user_profiles",
        createTypeInformation[UserProfileState]
      )

      descriptor.setQueryable("user_profiles_query")
      userProfileState = getRuntimeContext.getState(descriptor)
    }

    override def flatMap(value: UserProfileUpdate, out: Collector[UserProfile]): Unit = {

      val profile = if (userProfileState.value() == null) UserProfileState(value.userId, None, None, None, None, None, None) else userProfileState.value()

      val newProfile: UserProfileState = value match {
        case Event1UserProfileUpdate(e) => {
          events1Counter.add(1l)
          profile.copy(userValue1 = Some(e.getUserValue1), userValue2 = Some(e.getUserValue2), timeStamp = Some(e.getTimestamp))
        }

        case Event2UserProfileUpdate(e) => {
          events2Counter.add(1l)
          profile.copy(userValue3 =  Some(e.getUserValue3), userValue4 = Some(e.getUserValue4), timeStamp = Some(e.getTimestamp))
        }

        case UserScoreUpdate(s) => {
          scoredProfiles.add(1l)
          profile.copy(score = Some(s.getScore))
        }

      }
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

  implicit class ExtracedFeaturesOpts(ds: DataStream[ExtracedFeatures]) {
    def addKey(implicit tf: TypeInformation[(String, ExtracedFeatures)]) = ds.map(s => (s.getUserId, s))

  }

  implicit class EventsOpts(ds: DataStream[Event]) {

    def toUserProfileUpdate: DataStream[UserProfileUpdate] = {
      ds.map(s => Event1UserProfileUpdate(s))
    }
  }

  implicit class Events2Opts(ds: DataStream[Event2]) {

    def toUserProfileUpdate: DataStream[UserProfileUpdate] = {
      ds.map(s => Event2UserProfileUpdate(s))
    }
  }

  implicit class UserScoreOpts(ds: DataStream[UserScore]) {

    def toUserProfileUpdate: DataStream[UserProfileUpdate] = {
      ds.map(s => UserScoreUpdate(s))
    }
  }

  implicit class UserProfileBuilder(ds: DataStream[UserProfileUpdate]) {
    def buildUserProfile: DataStream[UserProfile] = ds
        .keyBy(_.userId)
        .flatMap(UserProfileBuilder)
  }

  implicit class UserProfileOpts(ds: DataStream[UserProfile]) {
    def extractFeatures = ds.flatMap(FeatureExtraction)
    def addKey = ds.map(s => (s.getUserId, s))
  }
}
