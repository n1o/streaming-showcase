//package io.mbarak.showcase.repo
//
//
//import io.mbarak.core.domain.{UserProfile.avsc, UserProfileBuilder}
//import org.apache.flink.api.common.{ExecutionConfig, JobID}
//import org.apache.flink.api.common.state.ValueStateDescriptor
//import org.apache.flink.api.common.typeinfo.{BasicTypeInfo, TypeHint, TypeInformation}
//import org.apache.flink.queryablestate.client.QueryableStateClient
//
//import scala.util.Try
//
//class UserProfileRepo {
//
//  val client = new QueryableStateClient("localhost", 6126)
//
//  val descriptor: ValueStateDescriptor[UserProfile.avsc] =  new ValueStateDescriptor[UserProfile.avsc](
//    "user_profiles",
//    TypeInformation.of(new TypeHint[UserProfile.avsc] {}).createSerializer(new ExecutionConfig()),
//    UserProfileBuilder.emptyProfile("")
//  )
//
//  val job = JobID.fromHexString("b42a00ff6011e46677606a7dffd695fa")
//  def user(userId: String): Option[UserProfile.avsc] = {
//
//    Try {
//      client
//        .getKvState(job, "user_profiles", userId, BasicTypeInfo.STRING_TYPE_INFO, descriptor)
//        .get()
//        .value()
//    }.toOption
//  }
//}
