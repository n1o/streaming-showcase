//package io.mbarak.showcase.repo
//
//import io.mbarak.core.domain.UserProfileBuilder
//import io.mbarak.showcase.UserProfile.avsc
//import org.apache.flink.api.common.{ExecutionConfig, JobID}
//import org.apache.flink.api.common.state.ValueStateDescriptor
//import org.apache.flink.api.common.typeinfo.{BasicTypeInfo, TypeHint, TypeInformation}
//import org.apache.flink.queryablestate.client.QueryableStateClient
//
//object Test {
//
//  def main(args: Array[String]): Unit = {
//
//
//    val client = new QueryableStateClient("192.168.8.102", 9069)
//
//    val descriptor: ValueStateDescriptor[UserProfile.avsc] =  new ValueStateDescriptor[UserProfile.avsc](
//      "user_profiles_query",
//      TypeInformation.of(new TypeHint[UserProfile.avsc] {}).createSerializer(new ExecutionConfig),
//      UserProfileBuilder.emptyProfile("")
//    )
//
//    val job = JobID.fromHexString("380326def51caff5434434fd2cdfb0ca")
//
//    val res = client
//      .getKvState(job, "user_profiles_query", "user_57", BasicTypeInfo.STRING_TYPE_INFO, descriptor)
//      .get()
//      .value()
//
//    println(res)
//
//  }
//}
