# sbt-partial-unification
Enable partial unification support (SI-2712 fix) for scala 2.10/2.11/2.12/2.13

This supports either the Scala 2.12/2.11.9+ support for partial unification
of types, or the compiler plugin written by Miles Sabin for Scala 2.10 or 2.11.8.

## Usage

Add the following to your SBT build (e.g. to `project/plugins.sbt`):

```scala
addSbtPlugin("org.lyranthe.sbt" % "partial-unification" % "1.0.0")
```

This will be automatically enabled for all projects.

## Changing the plugin version

The setting `partialUnificationModule` describes which plugin should be used to
fill in functionality for Scala 2.10.6+ and 2.11.8, and may be altered.

```scala
partialUnificationModule := "com.milessabin" % "si2712fix-plugin" % "1.2.0"
```

## Disabling for a project

To disable the plugin for a particular project, use the `.disablePlugins` method, e.g.:

```scala
myProj.disablePlugins(PartialUnification)
```
