package scalawork

object StringStuff {
  val line:String = "replace \" quote"            //> line  : String = replace " quote
  line.replaceAll("\"" , "\\\\\"");               //> res0: String = replace \" quote
  
  val str = "{u1@no.com, u2@no.com, u3@no.com}"   //> str  : String = {u1@no.com, u2@no.com, u3@no.com}
  str.stripPrefix("{").stripSuffix("}").trim.split(",")
                                                  //> res1: Array[String] = Array(u1@no.com, " u2@no.com", " u3@no.com")

  val str2 = str.stripPrefix("{").stripSuffix("}").trim
                                                  //> str2  : String = u1@no.com, u2@no.com, u3@no.com
  val str3 = "{" + str2.replace("u1@no.com", "").stripPrefix(",").stripSuffix(",").trim + "}"
                                                  //> str3  : String = {u2@no.com, u3@no.com}
                                                 
	val pending = "{u1@no.com, u2@no.com, u3@no.com}"
                                                  //> pending  : String = {u1@no.com, u2@no.com, u3@no.com}
	val a1 = pending.stripPrefix("{").stripSuffix("}").trim.split(",").map(_.trim)
                                                  //> a1  : Array[String] = Array(u1@no.com, u2@no.com, u3@no.com)
	val email = "u2@no.com"                   //> email  : String = u2@no.com
	val newpending = (pending.stripPrefix("{").stripSuffix("}").trim.split(",").map(_.trim)) diff ((email.split(",").map(_.trim)))
                                                  //> newpending  : Array[String] = Array(u1@no.com, u3@no.com)
  "{"+newpending.mkString(",")+"}"                //> res2: String = {u1@no.com,u3@no.com}
  
  
  val list=List(("e66e7ae1-e7ba-4c74-a36c-47ab61bf3967",	"1395782886",	"pid1"),
  ("e66e7ae1-e7ba-4c74-a36c-47ab61bf3967",	"1395782886",	"pid2"),
  ("e66e7ae1-e7ba-4c74-a36c-47ab61bf3967",	"1395782886",	"pid3"),
  ("e66e7ae1-e7ba-4c74-a36c-47ab61bf3969",	"1395792886",	"pid21"),
  ("e66e7ae1-e7ba-4c74-a36c-47ab61bf3969",	"1395792886",	"pid22")
  )                                               //> list  : List[(String, String, String)] = List((e66e7ae1-e7ba-4c74-a36c-47ab
                                                  //| 61bf3967,1395782886,pid1), (e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,1395782886
                                                  //| ,pid2), (e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,1395782886,pid3), (e66e7ae1-e
                                                  //| 7ba-4c74-a36c-47ab61bf3969,1395792886,pid21), (e66e7ae1-e7ba-4c74-a36c-47ab
                                                  //| 61bf3969,1395792886,pid22))
  val newList = list.filter(_._2 == "1395782886") //> newList  : List[(String, String, String)] = List((e66e7ae1-e7ba-4c74-a36c-4
                                                  //| 7ab61bf3967,1395782886,pid1), (e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,1395782
                                                  //| 886,pid2), (e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,1395782886,pid3))
  
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

  val myArray : Array[(String,String,String)] = new Array[(String,String,String)](0)
                                                  //> myArray  : Array[(String, String, String)] = Array()

  def func(e:(String,String,String)) = {
  	if(myArray.length == 0)
  		myArray :+ e

  }                                               //> func: (e: (String, String, String))Any
  val r2 = rows map (e => func(e))                //> r2  : Array[Any] = Array(Array((e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,139578
                                                  //| 2886,pid1)), Array((e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,1395782886,pid2)),
                                                  //|  Array((e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,1395782886,pid3)), Array((e66e
                                                  //| 7ae1-e7ba-4c74-a36c-47ab61bf3969,1395792886,pid21)), Array((e66e7ae1-e7ba-4
                                                  //| c74-a36c-47ab61bf3969,1395792886,pid22)))
  val r3 = r2                                     //> r3  : Array[Any] = Array(Array((e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,139578
                                                  //| 2886,pid1)), Array((e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,1395782886,pid2)),
                                                  //|  Array((e66e7ae1-e7ba-4c74-a36c-47ab61bf3967,1395782886,pid3)), Array((e66e
                                                  //| 7ae1-e7ba-4c74-a36c-47ab61bf3969,1395792886,pid21)), Array((e66e7ae1-e7ba-4
                                                  //| c74-a36c-47ab61bf3969,1395792886,pid22)))
  val share: Array[(String,String,String)] = new Array[(String,String,String)](0)
                                                  //> share  : Array[(String, String, String)] = Array()
	rows.foreach{ e =>
      val (sid, ts, pid) = e
      	if(share.length == 0) {
      		share :+ Array(sid, ts)
      	  println(share)
      	}
     }                                            //> [Lscala.Tuple3;@69dbb4d1
                                                  //| [Lscala.Tuple3;@69dbb4d1
                                                  //| [Lscala.Tuple3;@69dbb4d1
                                                  //| [Lscala.Tuple3;@69dbb4d1
                                                  //| [Lscala.Tuple3;@69dbb4d1
  val v = share                                   //> v  : Array[(String, String, String)] = Array()
  
  val s = Seq("apple", "oranges", "apple", "banana", "apple", "oranges", "oranges")
                                                  //> s  : Seq[String] = List(apple, oranges, apple, banana, apple, oranges, oran
                                                  //| ges)
  s.groupBy(l => l)                               //> res3: scala.collection.immutable.Map[String,Seq[String]] = Map(banana -> Li
                                                  //| st(banana), oranges -> List(oranges, oranges, oranges), apple -> List(apple
                                                  //| , apple, apple))
  s.groupBy(l => l).map(t => (t._1, t._2.length)) //> res4: scala.collection.immutable.Map[String,Int] = Map(banana -> 1, oranges
                                                  //|  -> 3, apple -> 3)

}