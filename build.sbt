name := "Scala Server"

organization := "org.spacesim"
 
scalaVersion := "2.12.0"

lazy val akkaVersion = "2.5.4"
 
version := "0.0.1-SNAPSHOT"

libraryDependencies ++= Seq(
	"io.spray" %%  "spray-json" % "1.3.3",
	"com.typesafe.akka" %% "akka-actor" % akkaVersion,
	"com.typesafe.akka" %% "akka-testkit" % akkaVersion,
	"org.scalatest" %% "scalatest" % "3.0.1" % "test"
)