package org.lyranthe.sbt

import sbt._
import sbt.Keys._

object PartialUnification extends AutoPlugin {
  override def requires = plugins.JvmPlugin
  override def trigger = allRequirements

  object autoImport {
    val partialUnificationModule = settingKey[ModuleID]("Module to add partial unification to scala 2.10/2.11")
  }
  import autoImport._

  def scalaVersionToDependencies(scalaVersion: String, partialUnificationModule: ModuleID): Seq[ModuleID] = {
    val pluginDependency = compilerPlugin(partialUnificationModule cross CrossVersion.full)
    scalaVersion match {
      case "2.10" => Seq(pluginDependency)
      case "2.11" => Seq(pluginDependency)
      case "2.12" => Seq.empty
    }
  }

  def scalaVersionToCompilerFlags(scalaVersion: String): Seq[String] = {
    scalaVersion match {
      case "2.12" => Seq("-Ypartial-unification")
      case _ => Seq.empty
    }
  }

  override val projectSettings = Seq(
    partialUnificationModule := "com.milessabin" % "si2712fix-plugin" % "1.2.0",
    libraryDependencies ++= scalaVersionToDependencies(scalaBinaryVersion.value, partialUnificationModule.value),
    scalacOptions ++= scalaVersionToCompilerFlags(scalaVersion.value)
  )
}
