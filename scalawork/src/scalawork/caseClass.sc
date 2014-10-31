package scalawork

case class AcceptedShare(
		 shareid: String,
		 senderUid: String,
		 senderEmail: String)

object caseClass {
	val list = List(new AcceptedShare("shareid1","senderUid1","senderEmail1"),
	new AcceptedShare("shareid1","senderUid2","senderEmail2"),
	new AcceptedShare("shareid11","senderUid2","senderEmail2"),
	new AcceptedShare("shareid3","senderUid3","senderEmail3") )
                                                  //> list  : List[scalawork.AcceptedShare] = List(AcceptedShare(shareid1,senderUi
                                                  //| d1,senderEmail1), AcceptedShare(shareid1,senderUid2,senderEmail2), AcceptedS
                                                  //| hare(shareid11,senderUid2,senderEmail2), AcceptedShare(shareid3,senderUid3,s
                                                  //| enderEmail3))
	val emails = list.map(e => e.senderEmail).distinct mkString (",")
                                                  //> emails  : String = senderEmail1,senderEmail2,senderEmail3

	val smtpVerifiedFrom="Kooboodle<no-reply@kooboodle.com>"
                                                  //> smtpVerifiedFrom  : String = Kooboodle<no-reply@kooboodle.com>
	val sentFromName = smtpVerifiedFrom.split("<")(0)
                                                  //> sentFromName  : String = Kooboodle
	val sentFrom = smtpVerifiedFrom.split("<").last.split(">")(0)
                                                  //> sentFrom  : String = no-reply@kooboodle.com

		val destinationPath = "/05718f30525456717270761/base/201410/af985a78a89549f18fa3f9bd2e9387ed.jpg"
                                                  //> destinationPath  : String = /05718f30525456717270761/base/201410/af985a78a89
                                                  //| 549f18fa3f9bd2e9387ed.jpg
		val dest = destinationPath.stripPrefix("/")
                                                  //> dest  : String = 05718f30525456717270761/base/201410/af985a78a89549f18fa3f9b
                                                  //| d2e9387ed.jpg

	val s ="123"                              //> s  : String = 123
	val s1 = "+"+s                            //> s1  : String = +123
	
	val pathBase = "http://dev-s31409051855.s3.amazonaws.com/2d59cd868d3454478953671/base/201410/948fac5ad19d469ab17ec73eb1af8f43.jpg"
                                                  //> pathBase  : String = http://dev-s31409051855.s3.amazonaws.com/2d59cd868d3454
                                                  //| 478953671/base/201410/948fac5ad19d469ab17ec73eb1af8f43.jpg
  val p = pathBase.split("://")(1)                //> p  : String = dev-s31409051855.s3.amazonaws.com/2d59cd868d3454478953671/base
                                                  //| /201410/948fac5ad19d469ab17ec73eb1af8f43.jpg
  p.substring(p.indexOf("/"))                     //> res0: String = /2d59cd868d3454478953671/base/201410/948fac5ad19d469ab17ec73
                                                  //| eb1af8f43.jpg
}