import play.Project._

val name = "java"

val version = "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

val module = RootProject(file("../../module"))

val java = project.in(file(".")).dependsOn(module)

play.Project.playJavaSettings
