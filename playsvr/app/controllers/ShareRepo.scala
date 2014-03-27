package controllers


object ShareRepo {
	def putSharePhotos(uid: String, pids: String, shareType: Option[String], recipients: String) = {
        val sharetype = shareType match {
          case Some("email") => Enum.SHARE_TYPE_EMAIL
          case _ => Enum.SHARE_TYPE_EMAIL
        }
        Configure.neo4jDatabase.putSharePhotos(uid, pids, sharetype, recipients)
	}
	
	def postSharePhotos(uid: String, shareid: String, status: String) = {
	  status match {
	    case "accept" => Configure.neo4jDatabase.postSharePhotosAccept(uid, shareid)
	    //case "ignore" => 
	    case _ => Configure.neo4jDatabase.postSharePhotosAccept(uid, shareid)
	  }  
	}
	
	def getSharePhotos(uid: String, shareid: Option[String], status: Option[String]) = {
	  (shareid, status) match {
	    case (Some(shareid), _) => Configure.neo4jDatabase.getSharePhotosByShareid(uid, shareid)
	    case (None, None) => Configure.neo4jDatabase.getSharePhotos(uid)
	    case (None, Some("sent")) => Configure.neo4jDatabase.getSharePhotosSent(uid)
	    case (None, Some("received")) => Configure.neo4jDatabase.getSharePhotosReceived(uid)
	    case (None, Some(status)) => Configure.neo4jDatabase.getSharePhotosReceived(uid)
	  }
	}


}