package scalawork

object util {
	def uuid(nchars: Int = 32) = scala.util.Random.alphanumeric.filter(_.isLetter).take(nchars).mkString
                                                  //> uuid: (nchars: Int)String
	def uuid2 = java.util.UUID.randomUUID.toString.replaceAll("-","")
                                                  //> uuid2: => String
	
	val id = uuid()                           //> id  : String = mYJGUtPeZgxEWgtCelAnuDZoAcySbObb
	val id2 = uuid2                           //> id2  : String = 5bec70acb7194b56894185aeeae7bd28
	
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
}