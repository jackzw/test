package scalawork

import scala.collection.mutable

object MapStuff {
  val lb = List("a1","a2","a3")                   //> lb  : List[String] = List(a1, a2, a3)
	val m = Map("a"->"","b"->lb,"c"->List("a5","a6"))
                                                  //> m  : scala.collection.immutable.Map[String,Object] = Map(a -> "", b -> List(
                                                  //| a1, a2, a3), c -> List(a5, a6))
	val a = m.get("a").get.toString           //> a  : String = ""
	a.isEmpty                                 //> res0: Boolean = true
	val b = m.get("b").get                    //> b  : Object = List(a1, a2, a3)
  
  lb.mkString(",")                                //> res1: String = a1,a2,a3
  
  val rows=List(("tony.clickfree@gmail.com",	"Tony CFGoogle",	"b00b9ebe343359488599481"),
("tytonytong@hotmail.com",	"Tony HM",	"e56702d091335083376707"),
("tytonytong@hotmail.com",	"Tony HM",	"e56702d091335083376707"),
("joseph.dev@clickfree.com",	"joseph dev",	"e311d13ed13357893569471"),
("joseph.dev@clickfree.com",	"joseph dev",	"e311d13ed13357893569471"))
                                                  //> rows  : List[(String, String, String)] = List((tony.clickfree@gmail.com,Tony
                                                  //|  CFGoogle,b00b9ebe343359488599481), (tytonytong@hotmail.com,Tony HM,e56702d0
                                                  //| 91335083376707), (tytonytong@hotmail.com,Tony HM,e56702d091335083376707), (j
                                                  //| oseph.dev@clickfree.com,joseph dev,e311d13ed13357893569471), (joseph.dev@cli
                                                  //| ckfree.com,joseph dev,e311d13ed13357893569471))
  rows.distinct                                   //> res2: List[(String, String, String)] = List((tony.clickfree@gmail.com,Tony C
                                                  //| FGoogle,b00b9ebe343359488599481), (tytonytong@hotmail.com,Tony HM,e56702d091
                                                  //| 335083376707), (joseph.dev@clickfree.com,joseph dev,e311d13ed13357893569471)
                                                  //| )
   val pids = List("pid1","pid2","pid3")          //> pids  : List[String] = List(pid1, pid2, pid3)
   val where = (pids map(pid => s"""p.pid='$pid'""")).mkString(" or ")
                                                  //> where  : String = p.pid='pid1' or p.pid='pid2' or p.pid='pid3'
   val params = mutable.Map[String, Any]("uid" -> "uid", "takenBefore" -> "takenBefore")
                                                  //> params  : scala.collection.mutable.Map[String,Any] = Map(takenBefore -> take
                                                  //| nBefore, uid -> uid)
   val takenAfter = Some(1234567)                 //> takenAfter  : Some[Int] = Some(1234567)
	 if(takenAfter.isDefined) params("takenAfter") = takenAfter.get
   val pid = Some("pid")                          //> pid  : Some[String] = Some(pid)
	 if(pid.isDefined) params("pid") = pid.get
 
  params                                          //> res3: scala.collection.mutable.Map[String,Any] = Map(pid -> pid, takenBefor
                                                  //| e -> takenBefore, uid -> uid, takenAfter -> 1234567)
}