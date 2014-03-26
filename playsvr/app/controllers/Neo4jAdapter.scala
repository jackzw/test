package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable
import java.io._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import org.anormcypher._
import java.util.UUID

 case class ConnectedUserInfo(
		 uid: String,
		 email: String,
		 fullname: String)
object ConnectedUserInfo {
	implicit val read = Json.reads[ConnectedUserInfo]
	implicit val write = Json.writes[ConnectedUserInfo]
}

object Neo4jAdapter {
  def listConnectedUsers(uid: String): Future[ApiResponse] = {
    val query = s"""
    	MATCH (m:User)-[:connected]-(n:User)
    	WHERE m.uid='$uid'
    	RETURN n.uid, n.email, n.fullname
    """

    val users = Cypher(query).apply().map(n => 
      ConnectedUserInfo(n[String]("n.uid"),n[String]("n.email"),n[String]("n.fullname"))
	).toList
	
	Future(ApiResponse(200, "Connected users", Json.obj("users" -> users)))
  }

  def createShare(uid: String, pidArray: Array[String], shareType: Int) = {
    val shareid = UUID.randomUUID().toString()
    val where = (pidArray map(pid => "p.pid='%s'" format (pid))).mkString(" or ")

    val query = s"""
    	MATCH (u:User {uid:'$uid'})-[r]->(pc:PhotoCollection)<-[:uploaded]-(p:Photo),
    		(u:User)-[l]->(pe:PersonalExchange)
    	WHERE $where
    	CREATE UNIQUE (p)-[:shared]->(s:Share {shareid:'$shareid', shareType:$shareType, timestamp:timestamp()/1000})<-[:shared_sent {timestamp:timestamp()/1000}]-(pe)
    """
    	
    if(Cypher(query).execute()) shareid else null
  }
  
  // crate batch share_to connected users
  def createSharedToConnectedUsers(uid: String, shareid: String, status: Int, emailArray: Array[String]) = {
    val where = (emailArray map(email => "u.email='%s'" format (email))).mkString(" or ")

    val query = s"""
    	MATCH (s:Share {shareid:'$shareid'}),
    	      (:User {uid:'$uid'})-[:connected_user]-(u:User),
    		  (u:User)-[:personal_exchange]->(pe:PersonalExchange) 
    	WHERE $where 
    	MERGE (s)-[r:shared_to]->(pe) 
    	ON CREATE SET r.status=$status 
    	RETURN u.email
    """

    val validated = Cypher(query).apply().map(u => 
      u[String]("u.email")
	).toArray
	
	val pending = emailArray diff validated
	
	Map("validated"->validated.mkString(","), "pending"->pending.mkString(","))
  }
  
  def sharePhotos(uid: String, pids: String, shareType: Int, recipients: String): Future[ApiResponse] = {
    val pidArray = pids.split(",").map(_.trim)
    val shareid = this.createShare(uid, pidArray, shareType)
    if(shareid == null) {
      Future(ApiResponse(500, "Error when create share node.", null))
    } else {
      val emailArray = recipients.split(",").map(_.trim)
      val processed = this.createSharedToConnectedUsers(uid, shareid, Enum.SHARE_STATUS_INVITED, emailArray)
      val shared_to = processed.get("validated") getOrElse ""
      val pending = processed.get("pending")  getOrElse ""
      if(!pending.isEmpty()) {
	    val query = s"""
	    	MATCH (u:User {uid:'$uid'})-[l]->(pe:PersonalExchange)-[:shared_sent]->(s:Share {shareid:'$shareid'})
	    	SET s.pending='{$pending}'
	    """
	    if(!Cypher(query).execute()) {
	      Future(ApiResponse(501, "Error when set share pending recipient.", null))
	    } else {
	    	Future(ApiResponse(200, "Photo shared", Json.obj("shared_to" -> shared_to, "pending" -> pending)))    
	    }
      } else {
	    	Future(ApiResponse(200, "Photo shared", Json.obj("shared_to" -> shared_to)))            
      }
    }
  }
  
  def sharePhotosAccept(uid: String, shareid: String): Future[ApiResponse] = {
    
    val query = s"""
		MATCH (u:User {uid:'$uid'})-[l]->(pe:PersonalExchange),
		(s:Share {shareid:'$shareid'})<-[ssr]-(:PersonalExchange)<-[per]-(o:User)
		WHERE has(s.pending) and s.pending =~ ('.'+u.email+'.*')
		CREATE UNIQUE (pe)<-[r:share_to {timestamp:timestamp()/1000}]-(s), (u)-[:connected_user]-(o)
		RETURN s.pending, u.email
	"""
		
    val processed = Cypher(query).apply().map(u => 
      u[String]("s.pending")->u[String]("u.email")
	).toArray
	
	if(processed.length == 0) {
    	Future(ApiResponse(200, "share accepted already", Json.obj("shareid" -> shareid)))    	  
	} else {
	  val pending = processed.apply(0)._1
	  val email = processed.apply(0)._2
	  val a = (pending.stripPrefix("{").stripSuffix("}").trim.split(",").map(_.trim)) diff ((email.split(",").map(_.trim)))
	  val newpending = "{"+a.mkString(",")+"}"
	  
	  val query = s"""
		MATCH (s:Share {shareid:'$shareid'})
		SET s.pending='$newpending'
	  """
	  if(Cypher(query).execute()) {
	    Future(ApiResponse(200, "share accepted", Json.obj("shareid" -> shareid, "accepted"->email, "pending" -> newpending)))	  
	  } else {	  
	    Future(ApiResponse(501, "Error when modify share pending.", null))
	  }
	}
  }

}
