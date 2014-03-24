package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable
import java.io._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import org.anormcypher._
import com.typesafe.plugin._

object myController extends ApiController {
  
	def listConnectedUsers(uid: Option[String]) = AuthAction.async {
        request => {
            val responseF = Configure.neo4jDatabase.listConnectedUsers(getUserId(request, uid))
    		responseF.map( response => apiResponse(response) )
        }
	}

	def sharePhotos(shareType: Option[Int], pids: String, recipients: String) = AuthAction.async {
        request => {
            val sharetype = shareType match {
              case Some(t) => t
              case _ => Enum.SHARE_TYPE_EMAIL
            }
            val responseF = Configure.neo4jDatabase.sharePhotos(request.uid, pids, sharetype, recipients)
    		responseF.map( response => apiResponse(response) )
        }
	}

}