name := "MyApp"

version := "0.1"

scalaVersion := "2.9.2"

libraryDependencies += "com.nicta" %% "scoobi" % "0.7.0-cdh4-SNAPSHOT"

scalacOptions ++= Seq("-Ydependent-method-types", "-deprecation")

resolvers ++= Seq(
    "nicta's avro" at "http://nicta.github.com/scoobi/releases",
    "cloudera" at "https://repository.cloudera.com/content/repositories/releases",
    "sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots")

