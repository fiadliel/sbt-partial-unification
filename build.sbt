enablePlugins(GitVersioning)

git.useGitDescribe := true

organization := "org.lyranthe.sbt"

name := "partial-unification"

sbtPlugin := true

crossSbtVersions := Seq("0.13.16", "1.0.0")

publishMavenStyle := false

bintrayPackageLabels := Seq("sbt", "si-2712", "partial-unification", "scala")

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
