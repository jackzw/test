package scalawork

object util {
	def uuid(nchars: Int = 32) = scala.util.Random.alphanumeric.filter(_.isLetter).take(nchars).mkString
                                                  //> uuid: (nchars: Int)String
	def uuid2 = java.util.UUID.randomUUID.toString.replace("-","")
                                                  //> uuid2: => String
	
	val id = uuid()                           //> id  : String = aaTKVQYTWNYEwUtuNmBAgBDqHLMqrdlL
	val id2 = uuid2                           //> id2  : String = d0d0ba2b1eee4b39b3f8858db9f3c6d1
	
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
}