package controllers

object myMockSessionAdapter {
    
    var uid: String = "uid1"

    def getUid(): String =
    {
        uid
    }

    def setUid(uid: String) {
        this.uid = uid;
    }  
}
