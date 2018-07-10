package org.lyranthe.sbt

import org.lyranthe.sbt.PartialUnification.Resolution
import org.scalatest.{Assertion, FlatSpec, Matchers}
import sbt._

class PartialUnificationSpec extends FlatSpec with Matchers {

  val partialUnificationPlugin: ModuleID =
    "com.milessabin" % "si2712fix-plugin" % "1.2.0"

  def pluginOrFlag(scalaVersion: String): PartialUnification.Resolution =
    PartialUnification.pluginOrFlag(scalaVersion, partialUnificationPlugin)

  def shouldFail(scalaVersion: String): Assertion = {
    a[RuntimeException] shouldBe thrownBy(pluginOrFlag(scalaVersion))
  }

  def shouldReturnPlugin(scalaVersion: String): Assertion = {
    pluginOrFlag(scalaVersion) shouldBe a[Resolution.Plugin]
  }

  def shouldReturnFlag(scalaVersion: String): Assertion = {
    pluginOrFlag(scalaVersion) shouldBe a[Resolution.Flag]
  }

  def shouldDoNothing(scalaVersion: String): Assertion = {
    pluginOrFlag(scalaVersion) shouldBe a[Resolution.NothingHappens.type]
  }

  "Scala version" should "not match 2.10.0" in {
    shouldFail("2.10.0")
  }

  it should "choose plugin 2.10.6" in {
    shouldReturnPlugin("2.10.6")
  }

  it should "choose plugin for 2.10.7" in {
    shouldReturnPlugin("2.10.7")
  }

  it should "not match 2.11.7" in {
    shouldFail("2.11.7")
  }

  it should "choose plugin for 2.11.8" in {
    shouldReturnPlugin("2.11.8")
  }

  it should "choose flag for 2.11.9" in {
    shouldReturnFlag("2.11.9")
  }

  it should "choose flag for 2.12.0" in {
    shouldReturnFlag("2.12.0")
  }

  it should "choose flag for 2.13.0-M1" in {
    shouldReturnFlag("2.13.0-M1")
  }

  it should "choose flag for 2.13.0-M2" in {
    shouldReturnFlag("2.13.0-M2")
  }

  it should "choose flag for 2.13.0-M3" in {
    shouldReturnFlag("2.13.0-M3")
  }

  it should "do nothing for 2.13.0-M4" in {
    shouldDoNothing("2.13.0-M4")
  }

  it should "do nothing for 2.13.0" in {
    shouldDoNothing("2.13.0")
  }

  it should "do nothing for 2.13.1-M1" in {
    shouldDoNothing("2.13.1-M1")
  }

  it should "do nothing for 3.0.0" in {
    shouldDoNothing("3.0.0")
  }
}
