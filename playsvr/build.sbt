name := "playsvr"

version := "1.0-SNAPSHOT"

resolvers ++= Seq(
  "anormcypher" at "http://repo.anormcypher.org/",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  //jdbc,
  //anorm,
  "org.apache.tomcat" % "tomcat-jdbc" % "8.0.3",
  cache,
  "com.typesafe.slick" %% "slick" % "2.0.0",
  "org.anormcypher" %% "anormcypher" % "0.4.4"
)     

play.Project.playScalaSettings
