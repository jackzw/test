package controllers

import org.apache.tomcat.jdbc.pool.DataSource
import org.apache.tomcat.jdbc.pool.PoolProperties
import scala.collection.JavaConverters._
 
// See this for more information on Tomcat's JDBC connection pool:
// http://ci.apache.org/projects/tomcat/tomcat8/docs/jdbc-pool.html#Standalone
object JDBCWrapper {
  
	val INITIAL_SIZE = 10
	val MAX_ACTIVE = 100
	val MAX_IDLE = Integer.MAX_VALUE
	val MIN_IDLE = INITIAL_SIZE
	val MAX_WAIT = 30000
	val VALIDATION_QUERY = ""
	val VALIDATION_INTERVAL = 30000
	val TEST_ON_BORROW = false
	//val testOnReturn = false
	//val testWhileIdle = false
	val TIME_BETWEEN_EVICTION_RUNS_MILLIS = 5000
	//val numTestsPerEvictionRun
	val MIN_EVICTABLE_IDLE_TIME_MILLIS = 60000
	val REMOVE_ABANDONED = false
	val REMOVE_ABANDONED_TIMEOUT = 60
	val LOG_ABANDONED = false
	val JDBC_INTERCEPTORS = null
 
  private lazy val properties = {
    
    val p = new PoolProperties
 
    p.setUrl(Configure.dbUrl)
    p.setDriverClassName(Configure.dbDriver)
    p.setUsername(Configure.dbUser)
    p.setPassword(Configure.dbPassword)
    
    p.setInitialSize(Configure.dbInitialSize.getOrElse(INITIAL_SIZE))
    p.setMaxActive(Configure.dbMaxActive.getOrElse(MAX_ACTIVE))
    p.setMaxIdle(Configure.dbMaxIdle.getOrElse(MAX_IDLE))
    p.setMinIdle(Configure.dbMinIdle.getOrElse(MIN_IDLE))
    p.setTimeBetweenEvictionRunsMillis(Configure.dbTimeBetweenEvictionRunsMillis.getOrElse(TIME_BETWEEN_EVICTION_RUNS_MILLIS))
    p.setMinEvictableIdleTimeMillis(Configure.dbMinEvictableIdleTimeMillis.getOrElse(MIN_EVICTABLE_IDLE_TIME_MILLIS))
    p.setValidationQuery(Configure.dbValidationQuery.getOrElse(VALIDATION_QUERY))
    p.setValidationInterval(Configure.dbValidationInterval.getOrElse(VALIDATION_INTERVAL))
    p.setTestOnBorrow(Configure.dbTestOnBorrow.getOrElse(TEST_ON_BORROW))
    p.setRemoveAbandoned(Configure.dbRemoveAbandoned.getOrElse(REMOVE_ABANDONED))
    p.setRemoveAbandonedTimeout(Configure.dbRemoveAbandonedTimeout.getOrElse(REMOVE_ABANDONED_TIMEOUT))
    p.setLogAbandoned(Configure.dbLogAbandoned.getOrElse(LOG_ABANDONED))
    p.setJdbcInterceptors(Configure.dbJdbcInterceptors.getOrElse(JDBC_INTERCEPTORS))
    
    /*Configure.config.getConfig("db.default").get.entrySet.map(param => {
    	val key = param._1
    	val value = param._2.unwrapped().toString()
    	println(s"DB CONFIG: $key -> $value")
    	props.setProperty(key, value)
      
    })*/
    
    println("JDBC: " + p)
	
    p
  }
 
  lazy val datasource = {
    val ds = new DataSource()
    ds.setPoolProperties(properties)
    //ds.purgeOnReturn()
    ds
  }
}