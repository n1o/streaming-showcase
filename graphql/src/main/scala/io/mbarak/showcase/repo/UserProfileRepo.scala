package io.mbarak.showcase.repo


import io.mbarak.core.domain.UserProfileState
import org.apache.flink.api.common.JobID
import org.apache.flink.api.common.state.ValueStateDescriptor
import org.apache.flink.api.common.typeinfo.BasicTypeInfo
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.queryablestate.client.QueryableStateClient

import scala.util.Try

case class UserProfileRepo(jobHexa: String) {

  val client = new QueryableStateClient("192.168.22.100", 9069)

  val descriptor: ValueStateDescriptor[UserProfileState] =  new ValueStateDescriptor[UserProfileState](
    "user_profiles",
    createTypeInformation[UserProfileState]
  )

  val job = JobID.fromHexString(jobHexa)

  def getUser(userId: String): Option[UserProfileState] = Try {
      client
        .getKvState(job, "user_profiles_query", userId, BasicTypeInfo.STRING_TYPE_INFO, descriptor)
        .get()
        .value()
    }.toOption

  def allUsers: List[UserProfileState] = Nil
}
