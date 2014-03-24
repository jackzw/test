package controllers

import slick.jdbc.JdbcBackend.Session
import scala.concurrent.Future

object Util {
	def uuid(nchars: Int = 32) = scala.util.Random.alphanumeric.filter(_.isLetter).take(nchars).mkString
	
	def getString(obj: Option[Any], default: String = null): String = {
		obj match {
			case Some(s: String) => s
			case _ => default
		}
	}
	
	def insertToJson(json: String, key: String, value: String): String = {
        val index = json.indexOf("{");
        if (index != -1) {
            val str = String.format("\"%s\":\"%s\",", key, value)
            new StringBuilder(json).insert(index + 1, str).toString
        } else {
            json
        }
	}
	
	def hashPassword(pass: String): String = {
		val md = java.security.MessageDigest.getInstance("SHA-1")
		val passSalt = pass + "-salt_for_passwords"
		md.digest(passSalt.getBytes("UTF-8")).map("%02x".format(_)).mkString
	}
	
	def queryDb[T](body:(Session) => T)/*(implicit execctx: ExecutionContext)*/ = {
    	Future[T]({
    		Configure.mySqlDb.withSession(body)
    	})(Contexts.dbExecutionContext)
	}  
}
