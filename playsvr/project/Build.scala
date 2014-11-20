import sbt._
import Keys._
import PlayProject._

object MyPlayServerBuild extends Build {

	//lazy val hades = ProjectRef(uri("../Hades"), "hades")

    lazy val root = Project(id = "myplaysvr",
                            base = file("."),
                            settings = Project.defaultSettings)//.dependsOn(hades % "test->test;compile->compile")
}