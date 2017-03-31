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

  def pluginOrFlag(scalaVersion: String, pluginModule: ModuleID): Either[ModuleID, String] = {
    val pluginDependency = Left(compilerPlugin(pluginModule cross CrossVersion.full))
    val flag = Right("-Ypartial-unification")
    val VersionNumber((major +: minor +: patch +: _), _, _) = scalaVersion

    (major, minor, patch) match {
      case (2, 10, p) if p >= 6 => pluginDependency
      case (2, 11, 8) => pluginDependency
      case (2, 11, p) if p >= 9 => flag
      case (2, 12, _) => flag
      case _ => sys.error(s"scala version $scalaVersion is not supported by this plugin.")
    }
  }

  override val projectSettings = Seq(
    partialUnificationModule := "com.milessabin" % "si2712fix-plugin" % "1.2.0",
    libraryDependencies ++= pluginOrFlag(scalaVersion.value, partialUnificationModule.value).left.toSeq,
    scalacOptions ++= pluginOrFlag(scalaVersion.value, partialUnificationModule.value).right.toSeq
  )
}
