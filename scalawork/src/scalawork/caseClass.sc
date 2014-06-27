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
}