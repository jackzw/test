package scalawork

object ListStuff {

  val rows=List(("{u2@no.com,u3@no.com,u4@no.com}",	2),
  ("{u2@no.com,u3@no.com}",	1)
  )                                               //> rows  : List[(String, Int)] = List(({u2@no.com,u3@no.com,u4@no.com},2), ({u2
                                                  //| @no.com,u3@no.com},1))
  val pending = "{u2@no.com,u3@no.com,u4@no.com}" //> pending  : String = {u2@no.com,u3@no.com,u4@no.com}
  val a = (pending.stripPrefix("{").stripSuffix("}").trim.split(",").map(_.trim)).toList
                                                  //> a  : List[String] = List(u2@no.com, u3@no.com, u4@no.com)
  val list = rows map ( row =>  {
  ((row._1.stripPrefix("{").stripSuffix("}").trim.split(",").map(_.trim)).toList, row._2)
  })                                              //> list  : List[(List[String], Int)] = List((List(u2@no.com, u3@no.com, u4@no.c
                                                  //| om),2), (List(u2@no.com, u3@no.com),1))
  
  def acc(emails :List[String], photosCount:Int):List[(String,Int)] = {
  	val items = emails map(i=>{(i,photosCount)})
  	items
  }                                               //> acc: (emails: List[String], photosCount: Int)List[(String, Int)]
  
  
  acc(list(0)._1,list(0)._2)                      //> res0: List[(String, Int)] = List((u2@no.com,2), (u3@no.com,2), (u4@no.com,2)
                                                  //| )
  val list2 = list map (l => {acc(l._1,l._2)})    //> list2  : List[List[(String, Int)]] = List(List((u2@no.com,2), (u3@no.com,2),
                                                  //|  (u4@no.com,2)), List((u2@no.com,1), (u3@no.com,1)))
                                                  
 
  val rows2=List(("[u2@no.com,u3@no.com,u4@no.com]"),
  ("[u2@no.com,u3@no.com]")
  )                                               //> rows2  : List[String] = List([u2@no.com,u3@no.com,u4@no.com], [u2@no.com,u3@
                                                  //| no.com])
 		val pendings = rows2 map (r => {
		  (r.stripPrefix("[").stripSuffix("]").trim.split(",").map(_.trim))
		})                                //> pendings  : List[Array[String]] = List(Array(u2@no.com, u3@no.com, u4@no.com
                                                  //| ), Array(u2@no.com, u3@no.com))
	    val pendingFriends = pendings.foldLeft(List[(String)]())((b,a) => b++a).distinct.map(email => {
	      //val user = getUserInfoByEmail(email)
	      //if(user!=null) user else ConnectedUserInfo("",email,"")
	      email
	    }).toList                             //> pendingFriends  : List[String] = List(u2@no.com, u3@no.com, u4@no.com)
    
   val pending2 = pendingFriends diff List("u3@no.com")
                                                  //> pending2  : List[String] = List(u2@no.com, u4@no.com)
    pending2                                      //> res1: List[String] = List(u2@no.com, u4@no.com)

}