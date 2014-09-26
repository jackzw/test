package scalawork

import java.security.MessageDigest


object util {
	def uuid = java.util.UUID.randomUUID.toString.replaceAll("-","")
                                                  //> uuid: => String

	def uuid2(nchars: Int = 32) = scala.util.Random.alphanumeric.filter(_.isLetter).take(nchars).mkString
                                                  //> uuid2: (nchars: Int)String
	
	val id = uuid                             //> id  : String = 3ab92327ccbf44b693fde871069c9123
	val id2 = uuid2()                         //> id2  : String = oiaDmmwRJOXQzjfRyJNyioicyzfRXhRp
	
	 def bytes2hex(bytes: Array[Byte], sep: Option[String] = None): String = {
		sep match {
			case None => bytes.map("%02x".format(_)).mkString
			case _ => bytes.map("%02x".format(_)).mkString(sep.get)
		}
	// bytes.foreach(println)
	}                                         //> bytes2hex: (bytes: Array[Byte], sep: Option[String])String
	
	def md5(s: String) = {
    java.security.MessageDigest.getInstance("MD5").digest(s.getBytes)
	}                                         //> md5: (s: String)Array[Byte]
	
	def sha1(s: String) = {
    java.security.MessageDigest.getInstance("SHA-1").digest(s.getBytes)
	}                                         //> sha1: (s: String)Array[Byte]
	
	def base64encode(s: String) = {
		val encoded = new sun.misc.BASE64Encoder().encode(s.getBytes)
		encoded
	}                                         //> base64encode: (s: String)String
	
	def base64decode(s: String) = {
		val decoded = new sun.misc.BASE64Decoder().decodeBuffer(s)
		decoded.map("%s".format(_)).mkString
	}                                         //> base64decode: (s: String)String
	
	val s = "jzw899+"+"Hello"                 //> s  : String = jzw899+Hello
	
	val bytes = s.getBytes                    //> bytes  : Array[Byte] = Array(106, 122, 119, 56, 57, 57, 43, 72, 101, 108, 1
                                                  //| 08, 111)
	bytes.toString                            //> res0: String = [B@54e94480
	
	val encs = base64encode(s)                //> encs  : String = anp3ODk5K0hlbGxv
	
	val decs = base64decode(encs)             //> decs  : String = 1061221195657574372101108108111
	
	//val ha = new sun.misc.BASE64Encoder().encode(md.digest(params.get("Foo").getBytes))

	md5("Hello")                              //> res1: Array[Byte] = Array(-117, 26, -103, 83, -60, 97, 18, -106, -88, 39, -
                                                  //| 85, -8, -60, 120, 4, -41)
	val token = "1df51bfdf508045bda96f0d958cd2304"
                                                  //> token  : String = 1df51bfdf508045bda96f0d958cd2304
  
}