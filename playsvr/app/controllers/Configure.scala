package controllers

import play.api._
import play.api.Play.current
import play.api.libs.json._
import scala.slick.jdbc.JdbcBackend._
import org.anormcypher._

object Configure {

	val config = play.api.Play.configuration
	
	val UNDEFINED = "undefined"
	
	// members and default value
    val env = config.getString("env").getOrElse(UNDEFINED)
    val langs = config.getString("application.langs").getOrElse(UNDEFINED)
    val titanHost = config.getString("titan.ext.host").getOrElse(UNDEFINED)
    val elasticCacheHost = config.getString("elasticcache.host").getOrElse(UNDEFINED)
    val sessionServer = config.getString("session.server").getOrElse(UNDEFINED)

    val neo4jHost = config.getString("neo4j.host").getOrElse(UNDEFINED)
    
   	lazy val neo4jDatabase = Neo4jAdapter

    //must be configured
    val dbDriver = config.getString("db.default.driver").getOrElse(UNDEFINED)
	val dbUrl = config.getString("db.default.url").getOrElse(UNDEFINED)
	val dbUser = config.getString("db.default.user").getOrElse(UNDEFINED)
	val dbPassword = config.getString("db.default.pass").getOrElse(UNDEFINED)

	//optional
	val dbInitialSize = config.getInt("db.default.initialSize")
	val dbMaxActive = config.getInt("db.default.maxActive")
	val dbMaxIdle = config.getInt("db.default.maxIdle")
	val dbMinIdle = config.getInt("db.default.minIdle")
	val dbTimeBetweenEvictionRunsMillis = config.getInt("db.default.timeBetweenEvictionRunsMillis")
	val dbMinEvictableIdleTimeMillis = config.getInt("db.default.minEvictableIdleTimeMillis")
	val dbValidationQuery = config.getString("db.default.validationQuery")
	val dbValidationInterval = config.getLong("db.default.validationInterval")
	val dbTestOnBorrow = config.getBoolean("db.default.testOnBorrow")
	val dbRemoveAbandoned = config.getBoolean("db.default.removeAbandoned")
	val dbRemoveAbandonedTimeout = config.getInt("db.default.removeAbandonedTimeout")
	val dbLogAbandoned = config.getBoolean("db.default.logAbandoned")
	val dbJdbcInterceptors = config.getString("db.default.jdbcInterceptors")
 
	val datasource = JDBCWrapper.datasource
	val mySqlDb = Database.forDataSource(datasource)

	def printInfo = {
		println
		println("Configuration: "+ env)
		println("SessionServer: "+ sessionServer)
		println("titanHost: "+ titanHost)
		println("neo4jHost: "+ neo4jHost)
		println("elasticCacheHost: "+ elasticCacheHost)
		println("neo4jHost: "+ neo4jHost)		
		println
	}
	
	def toJson: JsValue = {
	  val json = Json.obj(
	      "env" -> env,
	      "langs" -> langs
	      )
	  json
	}
	
	Neo4jREST.setServer(Configure.neo4jHost)
	
	printInfo
}
