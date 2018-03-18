//package io.mbarak.opts
//
//import io.mbarak.core.domain.UserProfileBuilder
//import io.mbarak.showcase._
//import org.apache.flink.api.common.accumulators.LongCounter
//import org.apache.flink.api.common.functions.RichFlatMapFunction
//import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
//import org.apache.flink.api.common.typeinfo.{TypeHint, TypeInformation}
//import org.apache.flink.configuration.Configuration
//import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction
//import org.apache.flink.streaming.api.scala.DataStream
//import org.apache.flink.util.Collector
//import org.apache.flink.api.scala._
//
//object ExtractedFeaturesOpts {
//  case class JoinedEvents(userId: String, event1: Option[Event], event2: Option[Event2])
//
//  case object EventsJoin extends RichCoFlatMapFunction[Event, Event2, JoinedEvents] {
//
//    var events1Counter: LongCounter = _
//    var events2Counter: LongCounter = _
//
//    override def open(parameters: Configuration): Unit = {
//      val rt = getRuntimeContext
//
//      events1Counter = rt.getLongCounter("EVENT_1_COUNTER")
//      events2Counter = rt.getLongCounter("EVENT_2_COUNTER")
//    }
//
//    override def flatMap1(value: Event, out: Collector[JoinedEvents]): Unit = {
//      events1Counter.add(1l)
//      out.collect(JoinedEvents(value.getUserId, Some(value), None))
//    }
//
//    override def flatMap2(value: Event2, out: Collector[JoinedEvents]): Unit = {
//      events2Counter.add(1l)
//      out.collect(JoinedEvents(value.getUserId, None, Some(value)))
//    }
//  }
//
//  case object FeatureExtraction extends RichFlatMapFunction[UserProfile, ExtracedFeatures] {
//    var featuresCounter: LongCounter = _
//
//    override def open(parameters: Configuration): Unit = {
//      featuresCounter = getRuntimeContext.getLongCounter("EXTRACTED_FEATURES")
//    }
//
//    override def flatMap(value: UserProfile, out: Collector[ExtracedFeatures]): Unit = {
//      extractFeatures(value).foreach(f => {
//          out.collect(f)
//          featuresCounter.add(1l)
//        }
//      )
//    }
//
//    def extractFeatures(state: UserProfile):  Option[ExtracedFeatures] = for {
//      userValue1 <- Option(state.getUserValue1)
//      userValue3 <- Option(state.getUserValue3)
//      featuresUpdates <- Option(state.getFeaturesUpdated)
//      (good, neutral, bad) <- Option(state.getUserValue4).map {
//        case "good" => (1,0,0)
//        case "neutral" => (0,1,0)
//        case "bad" => (0,0,1)
//      }
//    } yield (new ExtracedFeatures(state.getUserId, userValue1, userValue3, featuresUpdates,  good, neutral, bad))
//  }
//
//  case object FeaturesJoin extends RichCoFlatMapFunction[JoinedEvents, UserScore, UserProfile] {
//
//    var userProfile: ValueState[UserProfile] = _
//    var scoredProfiles: LongCounter = _
//
//    override def open(parameters: Configuration): Unit = {
//      val descriptor: ValueStateDescriptor[UserProfile] = new ValueStateDescriptor[UserProfile](
//        "user_profiles",
//        TypeInformation.of(new TypeHint[UserProfile]() {}),
//        UserProfileBuilder.emptyProfile("")
//      )
//
//      //descriptor.setQueryable("user_profiles_query")
//      userProfile = getRuntimeContext.getState(descriptor)
//      scoredProfiles = getRuntimeContext.getLongCounter("SCORED_PROFILES")
//    }
//
//    override def flatMap1(value: JoinedEvents, out: Collector[UserProfile]): Unit = {
//      val profile = Option(userProfile.value()).getOrElse(UserProfileBuilder.emptyProfile(value.userId))
//
//      if(profile.getUserId == "") {
//        profile.setUserId(value.userId)
//      }
//
//      value.event1.foreach(e => {
//        profile.setUserValue1(e.getUserValue1)
//        profile.setUserValue2(e.getUserValue2)
//        profile.setFeaturesUpdated(e.getTimestamp)
//        }
//      )
//
//      value.event2.foreach(e => {
//        profile.setUserValue3(e.getUserValue3)
//        profile.setUserValue4(e.getUserValue4)
//      }
//      )
//
//      userProfile.update(profile)
//      out.collect(profile)
//    }
//
//    override def flatMap2(value: UserScore, out: Collector[UserProfile]): Unit = {
//      val profile = Option(userProfile.value()).getOrElse(UserProfileBuilder.emptyProfile(value.getUserId))
//
//      if(profile.getUserId == "") {
//        profile.setUserId(value.getUserId)
//      }
//
//      profile.setScoreUpdated(value.getTimestamp)
//      profile.setUserScore(value.getScore)
//
//      scoredProfiles.add(1l)
//      userProfile.update(profile)
//      out.collect(profile)
//    }
//  }
//
//  implicit class ExtracedFeaturesOpts(ds: DataStream[ExtracedFeatures]) {
//    def addKey(implicit tf: TypeInformation[(String, ExtracedFeatures)]) = ds.map(s => (s.getUserId, s))
//
//  }
//
//  implicit class EventsOpts(ds: DataStream[Event]) {
//    def joinWithEvents2(dds: DataStream[Event2]) =
//      ds
//        .connect(dds)
//        .keyBy(_.getUserId, _.getUserId)
//        .flatMap(EventsJoin)
//  }
//
//  implicit class JoinedEventsOpts(ds: DataStream[JoinedEvents]) {
//    def joinWithScores(dds: DataStream[UserScore]) =
//      ds
//        .connect(dds)
//        .keyBy(_.userId, _.getUserId)
//        .flatMap(FeaturesJoin)
//  }
//
//  implicit class UserProfileOpts(ds: DataStream[UserProfile]) {
//    def extractFeatures = ds.flatMap(FeatureExtraction)
//  }
//}
