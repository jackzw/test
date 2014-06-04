package scalawork

object ListStuff {
	      //val emails = recipients.split(",").map(_.trim).toList

  List(1,2,3,4) diff List(1,2)                    //> res0: List[Int] = List(3, 4)
  List(1,2,3,4) filterNot List(1,2).contains      //> res1: List[Int] = List(3, 4)

	def recipientsToList(recipients: String) = recipients.toLowerCase.split(",").map(_.trim()).toList
                                                  //> recipientsToList: (recipients: String)List[String]

  val s1 = "Pid1, piD2 , pid3, pID4"              //> s1  : String = Pid1, piD2 , pid3, pID4
  
  val recipientlist = recipientsToList(s1)        //> recipientlist  : List[String] = List(pid1, pid2, pid3, pid4)
  val recipientlist2 = recipientlist diff List("pid4")
                                                  //> recipientlist2  : List[String] = List(pid1, pid2, pid3)
  recipientlist.filter(_ != "pid4")               //> res2: List[String] = List(pid1, pid2, pid3)
  
  s1.split(",")                                   //> res3: Array[String] = Array(Pid1, " piD2 ", " pid3", " pID4")
  val a1 = s1.toLowerCase.split(",").map(_.trim).toList
                                                  //> a1  : List[String] = List(pid1, pid2, pid3, pid4)
	a1.mkString(",")                          //> res4: String = pid1,pid2,pid3,pid4

	s1.split(",").map(_.trim).toList          //> res5: List[String] = List(Pid1, piD2, pid3, pID4)

  val s2 = "pid1, pid3"                           //> s2  : String = pid1, pid3
  s2.split(",")                                   //> res6: Array[String] = Array(pid1, " pid3")
  val a2 = s2.split(",").map(_.trim)              //> a2  : Array[String] = Array(pid1, pid3)
	a1.mkString(",")                          //> res7: String = pid1,pid2,pid3,pid4

  val a3 = a1 diff a2                             //> a3  : List[String] = List(pid2, pid4)
	
	val a4 = a1 diff a1                       //> a4  : List[String] = List()
	
	val rslt = Map("shared_to"->a2.mkString(","), "pending"->a3.mkString(","), "test"->a4.mkString(","))
                                                  //> rslt  : scala.collection.immutable.Map[String,String] = Map(shared_to -> pid
                                                  //| 1,pid3, pending -> pid2,pid4, test -> "")
	
	val shared_to = rslt.get("shared_to") getOrElse ""
                                                  //> shared_to  : String = pid1,pid3
	val pending = rslt.get("pending") getOrElse ""
                                                  //> pending  : String = pid2,pid4
	
	val test = rslt.get("test") getOrElse ""  //> test  : String = ""
	
  
  test.isEmpty                                    //> res8: Boolean = true
  
  val rows=Array(("e66e7ae1-e7ba-4c74-a36c-47ab61bf3967",	"1395782886",	"pid1"),
  ("e66e7ae1-e7ba-4c74-a36c-47ab61bf3967",	"1395782886",	"pid2"),
  ("e66e7ae1-e7ba-4c74-a36c-47ab61bf3967",	"1395782886",	"pid3"),
  ("e66e7ae1-e7ba-4c74-a36c-47ab61bf3969",	"1395792886",	"pid21"),
  ("e66e7ae1-e7ba-4c74-a36c-47ab61bf3969",	"1395792886",	"pid22")
  )                                               //> rows  : Array[(String, String, String)] = Array((e66e7ae1-e7ba-4c74-a36c-47
                                                  //| ab61bf3967,1395782886,pid1), (e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,13957828
                                                  //| 86,pid2), (e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,1395782886,pid3), (e66e7ae1
                                                  //| -e7ba-4c74-a36c-47ab61bf3969,1395792886,pid21), (e66e7ae1-e7ba-4c74-a36c-47
                                                  //| ab61bf3969,1395792886,pid22))
  val photos = rows.filter(_._1 == rows(0)._1) map (e=>e._3)
                                                  //> photos  : Array[String] = Array(pid1, pid2, pid3)
  val rest = rows.filter(_._1 != rows(0)._1)      //> rest  : Array[(String, String, String)] = Array((e66e7ae1-e7ba-4c74-a36c-47
                                                  //| ab61bf3969,1395792886,pid21), (e66e7ae1-e7ba-4c74-a36c-47ab61bf3969,1395792
                                                  //| 886,pid22))
  /*
  def func(rows:Array[(String,String,String)], result: Array[(String,String,Array[String])]) = {
  	if(rows.length == 0) result else {
  		val photos = rows.filter(_._1 == rows(0)._1) map (e=>e._3)
  		result :+ (rows(0)._1,rows(0)._2,photos)
  		val rest = rows.filter(_._1 != rows(0)._1)
  		this.func(rest, result)
  	}
  }*/
  
	val kuku = rows.groupBy(o => (o._1, o._2)).map(entry => {
		val ((shareid, timestamp), sharesList) = entry
		val pidsList = sharesList.foldLeft(List[String]())((acc, share) => acc :+ share._3)
		val res = (shareid, timestamp, pidsList)
		res
	})                                        //> kuku  : scala.collection.immutable.Iterable[(String, String, List[String])]
                                                  //|  = List((e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,1395782886,List(pid1, pid2, p
                                                  //| id3)), (e66e7ae1-e7ba-4c74-a36c-47ab61bf3969,1395792886,List(pid21, pid22))
                                                  //| )
	val shareList = rows.groupBy(o => (o._1, o._2)).map(entry => {
		val ((shareid, timestamp), sharesList) = entry
		val pidsList = sharesList.foldLeft(List[String]())((acc, share) => acc :+ share._3)
		val res = (shareid, timestamp, pidsList)
		res
	})                                        //> shareList  : scala.collection.immutable.Iterable[(String, String, List[Stri
                                                  //| ng])] = List((e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,1395782886,List(pid1, pi
                                                  //| d2, pid3)), (e66e7ae1-e7ba-4c74-a36c-47ab61bf3969,1395792886,List(pid21, pi
                                                  //| d22)))
	
}