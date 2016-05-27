# sbt-partial-unification
Enable partial unification support (SI-2712 fix) for scala 2.10/2.11/2.12

This supports either the Scala 2.12 support for partial unification of types, or the
compiler plugin written by Miles Sabin for Scala 2.10 or 2.11.

## Usage

Add the following to your SBT build (e.g. to `project/plugins.sbt`):

```scala
resolvers += Resolver.bintrayIvyRepo("fiadliel", "sbt-plugins")

addSbtPlugin("org.lyranthe.sbt" % "partial-unification" % "0.0.2")
```

This will be automatically enabled for all projects.

_Note:_ The plugin should soon be present in the default SBT plugin repository,
so the resolver settings will not then need to be changed.

## Changing the plugin version

The setting `partialUnificationModule` describes which plugin should be used to
fill in functionality for Scala 2.10 and 2.11, and may be updated.

```scala
partialUnificationModule := "com.milessabin" % "si2712fix-plugin" % "1.2.0"
```

## Disabling for a project

To disable the plugin for a particular project, use the `.disablePlugins` method, e.g.:

```scala
myProj.disablePlugins(PartialUnification)
```
