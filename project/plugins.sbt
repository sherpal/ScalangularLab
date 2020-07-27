addCompilerPlugin(
  "org.scalameta" % "semanticdb-scalac" % "4.3.10" cross CrossVersion.full
)

/** Explicitly adding dependency on Scala.js */
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.1.1")

/** Plugin for generating TypeScript declaration file. */
resolvers += Resolver.jcenterRepo
addSbtPlugin("eu.swdev" % "sbt-scala-ts" % "0.9")
scalacOptions += "-Yrangepos"

/** Plugin for generating Scala.js facades from TypeScript declaration file. */
resolvers += Resolver.bintrayRepo("oyvindberg", "converter")
addSbtPlugin("org.scalablytyped.converter" % "sbt-converter" % "1.0.0-beta18")

/** Plugin for managing npm dependencies. */
addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.18.0")
