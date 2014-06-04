package scalawork

import java.util.UUID

object session {
    val INVITE_STATUS_INVITED = 0                 //> INVITE_STATUS_INVITED  : Int = 0
    val INVITE_STATUS_ACCEPTED = 1                //> INVITE_STATUS_ACCEPTED  : Int = 1
    val INVITE_STATUS_IGNORED = 2                 //> INVITE_STATUS_IGNORED  : Int = 2

    def ConvertStatusToInt0(status: Option[String]):Option[Int] = {
      if(status.isDefined) {
        val str = status.get
        if(str equals("invite")) {
		    Some(INVITE_STATUS_INVITED)
		} else if(str equals("accepted")) {
		    Some(INVITE_STATUS_ACCEPTED)
		} else if(str.equals("ignored")) {
		    Some(INVITE_STATUS_IGNORED)
		} else {
		    None
		}
      } else {
        None
      }
    }                                             //> ConvertStatusToInt0: (status: Option[String])Option[Int]
    def ConvertStatusToInt(status: Option[String]):Option[Int] = {
    	status match {
    		case Some("invite") => Some(INVITE_STATUS_INVITED)
    		case Some("accepted") => Some(INVITE_STATUS_ACCEPTED)
    		case Some("ignored") => Some(INVITE_STATUS_IGNORED)
    		case _ => None
    	}
    }                                             //> ConvertStatusToInt: (status: Option[String])Option[Int]
    
    
  ConvertStatusToInt0(None)                       //> res0: Option[Int] = None
  ConvertStatusToInt0(Some("invite"))             //> res1: Option[Int] = Some(0)
  ConvertStatusToInt0(Some("accepted"))           //> res2: Option[Int] = Some(1)
  ConvertStatusToInt0(Some("ignored"))            //> res3: Option[Int] = Some(2)
  ConvertStatusToInt0(Some("blabla"))             //> res4: Option[Int] = None
  
  ConvertStatusToInt(None)                        //> res5: Option[Int] = None
  ConvertStatusToInt(Some("invite"))              //> res6: Option[Int] = Some(0)
  ConvertStatusToInt(Some("accepted"))            //> res7: Option[Int] = Some(1)
  ConvertStatusToInt(Some("ignored"))             //> res8: Option[Int] = Some(2)
  ConvertStatusToInt(Some("blabla"))              //> res9: Option[Int] = None
  
	def uuid(nchars: Int = 32) = scala.util.Random.alphanumeric.filter(_.isLetter).take(nchars).mkString
                                                  //> uuid: (nchars: Int)String

	val uid = uuid(40)                        //> uid  : String = CPmnzCIaxvKLVYWKXFzxnmQQTVfLnkJNmgwNPbvU
	
	val id = UUID.randomUUID().toString()     //> id  : String = 58b29f42-50ca-43d9-bfb7-dbfeb38c5a0b
	UUID.randomUUID().toString().filterNot("-".toSet)
                                                  //> res10: String = d0c4acc8f0784f37943fc13fb1984d54
	
	val toRemove = "-".toSet                  //> toRemove  : scala.collection.immutable.Set[Char] = Set(-)
val words = id.filterNot("-".toSet)               //> words  : String = 58b29f4250ca43d9bfb7dbfeb38c5a0b
}