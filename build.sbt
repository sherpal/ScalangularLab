name := "scalangularlab"

version := "0.1"

scalaVersion := "2.13.2"

/** npm module will have version "0.1.0" */
scalaTsModuleVersion := (_ + ".0")

/** Enabling ScalaJS */
enablePlugins(ScalaJSPlugin)

/** Enabling ScalaTS */
enablePlugins(ScalaTsPlugin)

/** Enabling Scalably typed, with scala-js-bundler */
//enablePlugins(ScalablyTypedConverterPlugin)

/** Enabling Scalably typed, without scala-js-bundler */
enablePlugins(ScalablyTypedConverterExternalNpmPlugin)

def nodeModulesDirectory(baseDir: File) =
  baseDir / "webapp" / "Laboratory"

externalNpm := {
  //Process("npm", nodeModulesDirectory(baseDirectory.value)).!
  nodeModulesDirectory(baseDirectory.value)
}

val copyFileTask = taskKey[Unit]("Copy the compiled files into Angular modules")

copyFileTask := {
  IO.listFiles(baseDirectory.value / "target" / "scala-2.13")
    .filter(_.isFile)
    .foreach { file =>
      IO.copyFile(
        file,
        baseDirectory.value / "webapp" / "Laboratory" / "node_modules" / "scalangularlab" / file.name
      )
    }
}

addCommandAlias("makeModule", ";scalaTsFastOpt;copyFileTask")

val wantedNpmDeps = List(
  "@angular/common",
  "@angular/core",
  "@angular/forms",
  "@angular/router",
  "rxjs"
)
stIgnore := {
  val dir                   = nodeModulesDirectory(baseDirectory.value)
  val nodeModules           = dir / "node_modules"
  val nodeModulesPathLength = nodeModules.getAbsolutePath.length

  def allFiles(directory: File): List[File] =
    IO.listFiles(directory)
      .flatMap(file => file :: (if (file.isDirectory) allFiles(file) else Nil))
      .toList

  allFiles(nodeModules)
    .filter(_.isDirectory)
    .filter(d => IO.listFiles(d).map(_.getName).contains("package.json"))
    .map(_.getAbsolutePath.drop(nodeModulesPathLength + 1))
    .filterNot(wantedNpmDeps contains _)
}

libraryDependencies += "com.propensive" %%% "magnolia" % "0.16.0"
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided

libraryDependencies += "dev.zio" %%% "zio" % "1.0.0-RC21-2"
libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.0.0"
