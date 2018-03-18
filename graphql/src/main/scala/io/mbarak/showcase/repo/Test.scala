package io.mbarak.showcase.repo

import io.mbarak.core.domain.UserProfileState
import org.apache.flink.api.common.{ExecutionConfig, JobID}
import org.apache.flink.api.common.state.ValueStateDescriptor
import org.apache.flink.api.common.typeinfo.{BasicTypeInfo, TypeHint, TypeInformation}
import org.apache.flink.queryablestate.client.QueryableStateClient
import org.apache.flink.api.scala._

///192.168.8.102:9069

object Test {

  def main(args: Array[String]): Unit = {

    val client = new QueryableStateClient("192.168.8.102", 9069)

    val descriptor: ValueStateDescriptor[UserProfileState] =  new ValueStateDescriptor[UserProfileState](
      "user_profiles",
      createTypeInformation[UserProfileState]
    )

    val job = JobID.fromHexString("f1b2f7592fb0331712159af3cd13fc40")

    val res = client
      .getKvState(job, "user_profiles_query", "user_57", BasicTypeInfo.STRING_TYPE_INFO, descriptor)
      .get()
      .value()

    println(res)

  }
}
