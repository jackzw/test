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

object ShareController extends ApiController {
  
	def putSharePhotos(shareType: Option[String], pids: String, recipients: String) = AuthActionWithStats.async {
        request => {
            val responseF = ShareRepo.putSharePhotos(request.uid, pids, shareType, recipients)
    		responseF.map( response => apiResponse(response) )
        }
	}
	
	def postSharePhotos(shareid: String, status: String) = AuthActionWithStats.async {
        request => {
            val responseF = ShareRepo.postSharePhotos(request.uid, shareid, status)
    		responseF.map( response => apiResponse(response) )
        }
	}

	def getSharePhotos(shareid: Option[String], status: Option[String]) = AuthActionWithStats.async {
        request => {
            val responseF = ShareRepo.getSharePhotos(request.uid, shareid, status)
    		responseF.map( response => apiResponse(response) )
        }
	}	

/*
 CREATE (u:User {uid: 'mock', fullname:"mock", email: 'u1@no.com'})-[:photo_collection]->(pc:PhotoCollection),
	(u)-[:personal_exchange]->(:PersonalExchange),
	(:PhotoCollection)<-[:photo_collection]-(:User {uid: 'uid2', fullname:"uid2", email: 'u2@no.com'})-[:personal_exchange]->(:PersonalExchange),
	(:PhotoCollection)<-[:photo_collection]-(:User {uid: 'uid3', fullname:"uid3", email: 'u3@no.com'})-[:personal_exchange]->(:PersonalExchange),
        	(:Photo {pid:'pid1'})-[:uploaded]->(pc),
        	(:Photo {pid:'pid2'})-[:uploaded]->(pc),
        	(:Photo {pid:'pid3'})-[:uploaded]->(pc),
        	(:Photo {pid:'pid4'})-[:uploaded]->(pc),
        	(:Photo {pid:'pid5'})-[:uploaded]->(pc)
 */
}
