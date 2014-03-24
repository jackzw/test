package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws.WS
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._
import java.util.concurrent.TimeoutException
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.duration._
import play.Application._

object VersionController extends ApiController {

  def version = Action {
    
	  val json = Json.obj(
	      "build" -> "1.0.1",
	      "Configure" -> Configure.toJson,
	      "Ref" -> "2014-03-12-1")
	  
	  Ok(json)
  }

}