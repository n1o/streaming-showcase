package io.mbarak.showcase.schema

import io.mbarak.core.domain.UserProfileState
import io.mbarak.showcase.repo.UserProfileRepo
import sangria.macros.derive._
import sangria.schema.{Field, _}
import sangria.schema._
import sangria.macros._


object UserProfileDefinition {

  val UserProfileType = deriveObjectType[Unit, UserProfileState](
    ObjectTypeName("UserProfile")
  )

  val UserId = Argument("userId", StringType)

  val QueryType = ObjectType("Query", fields[UserProfileRepo, Unit](
    Field("userProfile", OptionType(UserProfileType),
      description = Some("Returns a product with specific `id`."),
      arguments = UserId :: Nil,
      resolve = c => c.ctx.getUser(c arg UserId)
      )
  ))
  val schema = Schema(QueryType)


  val query =
    graphql"""
    query UserProfile {
      userProfile(userId: "user_2") {
        userId
        userValue1
        userValue2
        userValue3
        userValue4
        score
        timeStamp
      }
    }
  """
}
