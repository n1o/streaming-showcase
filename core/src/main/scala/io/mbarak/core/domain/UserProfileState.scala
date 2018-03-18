package io.mbarak.core.domain

case class UserProfileState(userId: String, userValue1: Option[Double], userValue2: Option[Long], userValue3: Option[Double], userValue4: Option[String], score: Option[Int], timeStamp: Option[Long])
