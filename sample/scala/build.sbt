name := "scala"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)

val module = RootProject(file("../../module"))

val java = project.in(file(".")).dependsOn(module)

play.Project.playScalaSettings
