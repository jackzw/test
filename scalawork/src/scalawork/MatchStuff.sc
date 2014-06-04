package scalawork

object MatchStuff {
  val n = 20                                      //> n  : Int = 20
  val res = if(n<2) "1" else if(n<6) "2-5" else if(n<26) "6-25" else if(n<50) "26-50" else "50-plus"
                                                  //> res  : String = 6-25
  res + "-connections"                            //> res0: String = 6-25-connections
  
  val s ="nataly.june1@gmail.com;"                //> s  : String = nataly.june1@gmail.com;
 
	def formatPending(str: String)  = {
		if(str.indexOfSlice("[") < 0) {
			  val newstr = "[\""+ str.stripSuffix(";") +"\"]"
			  newstr
	  } else {
	  		str
	  }
	 }                                        //> formatPending: (str: String)String
 
 	formatPending(s)                          //> res1: String = ["nataly.june1@gmail.com"]
 
  val list = List("nataly.june1@gmail.com", 5)    //> list  : List[Any] = List(nataly.june1@gmail.com, 5)
  
  
}