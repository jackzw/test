import play.api._
import java.io.File
import com.typesafe.config.ConfigFactory
import play.api.libs.JNDI
import javax.naming.NamingException
import controllers.Configure
import org.anormcypher.Neo4jREST

object Global extends GlobalSettings {

    override def onLoadConfig(config: Configuration, path: File, classloader: ClassLoader, mode: Mode.Mode): Configuration = {

        val confFilePath = try {
            val confFilePath = JNDI.initialContext.lookup("java:comp/env/ZEUS_CONF_FILE_PATH").asInstanceOf[String]
            confFilePath
        } catch {
            case e: NamingException => {
            	val userDirConfPath = new File(System.getProperty("user.dir") + "/application.conf")
            	val defaultConfPath = userDirConfPath.exists() match {
            	  	case true => userDirConfPath.getAbsolutePath()
            	  	case false => path.getAbsolutePath() + "/conf/application.local.conf"
            	}
                defaultConfPath
            }
        }

        //config.getString("conf.file.path").getOrElse("application.dev.conf")
        println("Configuration file path: " + confFilePath)
        val envSpecificConfig = config ++ Configuration(ConfigFactory.parseFile(new File(confFilePath)))
        super.onLoadConfig(envSpecificConfig, path, classloader, mode)
    }

    override def onStart(app: Application) {
		
    }
}
