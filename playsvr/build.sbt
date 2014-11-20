name := "playsvr"

version := "1.0-SNAPSHOT"

resolvers ++= Seq(
  "anormcypher" at "http://repo.anormcypher.org/",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  //jdbc,
  //anorm,
		"com.typesafe.play" %% "play-json" % "2.2.3",
		"org.apache.tomcat" % "tomcat-jdbc" % "8.0.3",
		cache,
		"com.typesafe.slick" %% "slick" % "2.0.0",
		"org.anormcypher" %% "anormcypher" % "0.4.4",
		"com.typesafe" %% "play-plugins-mailer" % "2.2.0",
		"org.scalatest" % "scalatest_2.10" % "2.1.0" % "test",
  		"com.amazonaws" % "aws-java-sdk" % "1.7.1",
  		"com.twitter" %% "ostrich" % "9.4.2",
  		"org.graylog2" %% "gelfclient" % "1.0.0"
)     

play.Project.playScalaSettings
