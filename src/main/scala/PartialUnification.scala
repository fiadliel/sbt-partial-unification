package org.lyranthe.sbt

import sbt._
import sbt.Keys._

object PartialUnification extends AutoPlugin {
  override def requires = plugins.JvmPlugin
  override def trigger = allRequirements

  object autoImport {
    val partialUnificationModule = settingKey[ModuleID](
      "Module to add partial unification to scala 2.10/2.11")
  }

  import autoImport._

  def reportError(scalaVersion: String) = {
    sys.error(s"scala version $scalaVersion is not supported by this plugin.")
  }

  def pluginOrFlag(scalaVersion: String, pluginModule: ModuleID): Resolution = {
    val pluginDependency =
      Resolution.Plugin(compilerPlugin(pluginModule cross CrossVersion.full))
    val flag = Resolution.Flag("-Ypartial-unification")
    val VersionNumber((major +: minor +: patch +: _), tags, _) = scalaVersion

    (major, minor, patch, tags) match {
      case (2, 10, p, _) if p >= 6 => pluginDependency
      case (2, 10, _, _)           => reportError(scalaVersion)

      case (2, 11, 8, _)           => pluginDependency
      case (2, 11, p, _) if p >= 9 => flag
      case (2, 11, _, _)           => reportError(scalaVersion)

      case (2, 12, _, _) => flag

      case (2, 13, 0, milestone +: _)
          if milestone == "M1" || milestone == "M2" || milestone == "M3" =>
        flag // ignoring the M4 snapshot

      case _ => Resolution.NothingHappens
    }
  }

  override val projectSettings = Seq(
    partialUnificationModule := "com.milessabin" % "si2712fix-plugin" % "1.2.0",
    libraryDependencies ++= {
      pluginOrFlag(scalaVersion.value, partialUnificationModule.value) match {
        case Resolution.Plugin(id) => Seq(id)
        case _                     => Seq.empty
      }
    },
    scalacOptions ++= {
      pluginOrFlag(scalaVersion.value, partialUnificationModule.value) match {
        case Resolution.Flag(flag) => Seq(flag)
        case _                     => Seq.empty
      }
    }
  )

  sealed trait Resolution extends Product with Serializable

  object Resolution {
    final case class Plugin(id: ModuleID) extends Resolution
    final case class Flag(flag: String) extends Resolution
    case object NothingHappens extends Resolution
  }
}
