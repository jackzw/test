package controllers

import scala.concurrent.ExecutionContext
import play.api.libs.concurrent.Akka
import play.api.Play.current

object Contexts {
	implicit val dbExecutionContext: ExecutionContext = Akka.system.dispatchers.lookup("akka.actor.db-context")
	implicit val httpExecutionContext: ExecutionContext = Akka.system.dispatchers.lookup("akka.actor.http-context")
}