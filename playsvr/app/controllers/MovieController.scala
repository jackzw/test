package controllers

import play.api._
import play.api.mvc._
import org.anormcypher._
import org.anormcypher.CypherParser._
import java.util.UUID
import play.api.libs.json.Json

object MovieController extends ApiController {
  
  	val SHARE_TYPE_EMAIL 		= 0
	val SHARE_TYPE_FACEBOOK   	= 1
	val SHARE_TYPE_TWITTER    	= 2
	val SHARE_TYPE_GOOGLEPLUS 	= 3

  def list = Action {

    /*
	val result: Boolean = Cypher("START n=node(*) RETURN count(n) as count").execute()
	  
	  println("result: "+result)
	  */
	  /*
// create some sample data
val result2 = Cypher("""
  create (germany {name:"Germany", population:81726000, type:"Country", code:"DEU"}),
         (france {name:"France", population:65436552, type:"Country", code:"FRA", indepYear:1790}),
         (monaco {name:"Monaco", population:32000, type:"Country", code:"MCO"});
  """).execute()
// result2: Boolean = true
	  println("result2: "+result2)

	  
val cypherQuery = Cypher(
  """
    start n=node(*) 
    match n-->m
    where n.code = 'FRA';
    return n,m;
  """
)
Cypher(
  """
    start n=node(*) 
    where n.type! = "Country"
      and n.code! = {countryCode}
    return n.name
  """
).on("countryCode" -> "FRA")

// Create Cypher query
val allCountries = Cypher("start n=node(*) where n.type! = 'Country' return n.code as code, n.name as name")

// Transform the resulting Stream[CypherRow] to a List[(String,String)]
val countries = allCountries.apply().map(row => 
  row[String]("code") -> row[String]("name")
).toList

println("allCountries: "+allCountries)
	  
println("countries: "+countries)
*/

	//println("movies: " + this.getMovies)

	println("version: " + this.getVersion)

	println("schema: " + this.schema)

	// create user
	val email = "u1@no.com"
	val fullname = "user 1"
	val user1 = this.safeCreateUser(email, fullname)
	println("safeCreateUser user1: " + user1)

	val email2 = "u2@no.com"
	val fullname2 = "user 2"
	val user2 = this.safeCreateUser(email2, fullname2)
	println("safeCreateUser user2: " + user2)
	
	val email3 = "u3@no.com"
	val fullname3 = "user 3"
	val user3 = this.safeCreateUser(email3, fullname3)
	println("safeCreateUser user3: " + user3)
	
	//this.deleteUser(email)

	//val share = this.createShare(user1.uid, "pid1,pid2,pid3", SHARE_TYPE_EMAIL, "u2@no.com,u3@no.com")	
	//println("create share: " + share)
	  
	//val share2 = this.createShare(user1.uid, "pid4,pid5", SHARE_TYPE_EMAIL, "u2@no.com")	
	//println("create share2: " + share2)
	
	val users = this.listConnectedUsers("cc4d4056-1eec-4f5a-b6d0-40a82a49fd58")
	println("user1 connected users: " + users)
	
	apiOk(Json.obj("users" -> users), "Connected users.")
	//apiError()	
  }
  
  def getMovies = {
	// Create Cypher query
	val allMovies = Cypher("start n=node(*) where n:Movie return n.title as title, n.released as released")
	// Transform the resulting Stream[CypherRow] to a List[(String,String)]
	val movies = allMovies.apply().map(row => 
	  row[String]("title") -> row[Long]("released")
	).toList
    movies
  }
  
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  def getVersion = {
	val query = Cypher("start n=node(*) where n:Admin return n.version as version")
	// Transform the resulting Stream[CypherRow] to a List[(Long)]
	val rows = query.apply().map(row => 
	  row[String]("version")
	).toList
	if(rows.isEmpty) None
	else rows.apply(0)
  }
  
  def schema = {
	if(!Cypher("CREATE CONSTRAINT ON (n:User) ASSERT n.uid IS UNIQUE").execute()) false
	else if(!Cypher("CREATE CONSTRAINT ON (n:User) ASSERT n.email IS UNIQUE").execute()) false
	else if(!Cypher("CREATE CONSTRAINT ON (n:Share) ASSERT n.shareid IS UNIQUE").execute()) false
	true
  }
  
 val ACCOUNT_STATUS_NORMAL  = 0
 val ACCOUNT_STATUS_DELETED = 1

  def createUser(email: String, fullname: String) = {
    val userid = UUID.randomUUID().toString()
    val timestamp = System.currentTimeMillis / 1000
    val status = ACCOUNT_STATUS_NORMAL
    val query = s"create (n:User {uid:'$userid', email:'$email', fullname:'$fullname', status:$status, timestamp:timestamp()/1000})"
    println(query)
	Cypher(query).execute()
  }

  def getUser(email: String): Option[ConnectedUserInfo] = {
    val query = s"match n where n:User and n.email='$email' return n.uid, n.email, n.fullname"
    //println(query)
    val users = Cypher(query).apply().map(n => 
      ConnectedUserInfo(n[String]("n.uid"),n[String]("n.email"),n[String]("n.fullname"))
	).toList
    if(users.isEmpty) None
    else Some(users.apply(0))
  }

  def safeCreateUser(email: String, fullname: String): ConnectedUserInfo = {
    getUser(email) match {
      case None => {
        createUser(email, fullname)
        safeCreateUser(email, fullname)
      }
      case Some(u) => u
    }
  }

  def deleteUser(email: String) = {
    val query = s"match n where n:User and n.email='$email' delete n"
    //println(query)
	val result = Cypher(query).execute()
	result
  }

  def listConnectedUsers(uid: String) = {
    val query = s"""
    	MATCH (m:User)-[:connected]-(n:User)
    	WHERE m.uid='$uid'
    	RETURN n.uid, n.email, n.fullname
    """

    val users = Cypher(query).apply().map(n => 
      ConnectedUserInfo(n[String]("n.uid"),n[String]("n.email"),n[String]("n.fullname"))
	).toList
	
	users
  }
  def MapToMatch(properties: Map[String, Any]) = {
    val kvs = (properties map { 
      	case (key, value) => { (value) match {
      	  case (Int) => "%s:%s" format (key, value)
      	  case (Long) => "%s:%s" format (key, value)
      	  case _ => "%s:'%s'" format (key, value)
      	}
      	}
      }).mkString(",")
    kvs
  }
  
  def MapToSet(properties: Map[String, Any], n: String = "n") = {
    val kvs = (properties map { 
      	case (key, value) => { (value) match {
      	  case (Int) => "%s.%s=%s" format (n, key, value)
      	  case _ => "%s.%s='%s'" format (n, key, value)
      	}
      	}
      }).mkString(",")
    kvs     
  }
  
  def createNode(label: String, properties: Map[String, Any]) = {
    val kvs = MapToMatch(properties)
    val query = s"create (n:$label {$kvs})"
    println(query)
    Cypher(query).execute()
  }

  def createEdge(aProperties: Map[String, Any], bProperties: Map[String, Any]) = {
    val query = s"""
    MATCH (a:User {email:'u1@no.com'}),
      (b:Share {shareid:'2cb5be96-42f7-45d3-bbfe-9485d78f5a11'})
    MERGE (a)<-[r:shared_to]-(b)
    """
    Cypher(query).execute()
  }
  
  def createShare(userid: String, pids: String, shareType: Int, shareTo: String) = {
    val uid = UUID.randomUUID().toString()
    val query = s"create (n:Share {shareid:'$uid', shareType:$shareType, timestamp:timestamp()/1000})"
    println(query)
	val result = Cypher(query).execute()
	
   	val pidList = pids.split(",")
   	for ( pid <- pidList ) {
   	  // find photo node and link to share node
   	}

   	val sendToList = shareTo.split(",")
   	for ( email <- sendToList ) {
   	  // find user node and send email
   	  val user = this.getUser(email)
   	}
   	
   	result
  }


/*
  def getUser(email: String) = {
	val result:List[(String,String,String)] = {
	  Cypher("start n=node(*) where n:User return n.uid, n.email, n.fullname").as(
	    str("n.uid") ~ str("n.email") ~ str("n.fullname") map(flatten) *
	  ) 
	}
	println("result: " + result.apply(0))
  }
*/
 
}