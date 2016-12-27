name := """ReactiveWebStore"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.webjars" %% "webjars-play" % "2.5.0",
  "com.adrianhurt" %% "play-bootstrap" % "1.1-P25-B3",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

