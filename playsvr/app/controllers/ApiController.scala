package controllers

import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.Future
import play.Logger
import com.twitter.ostrich.stats.Stats

trait ApiController extends Controller {
  
	def apiOk(result: JsValue, message: String = ""): SimpleResult = {
		Ok(Json.obj( "code" -> 200, 
		    "message" -> message,
		    "result" -> result))
	}

	def apiError(message: String="Not implemented.", code: Int = 500): SimpleResult = {
		InternalServerError(Json.obj( "code" -> code, 
		    "message" -> message))
	}
	
	def apiResponse(response: ApiResponse): SimpleResult = {
		if(response.Code == 200) {
			Ok(Json.toJson(response))
		} else {
			InternalServerError(Json.obj( "code" -> response.Code, "message" -> response.Message))
		}
	}
	
	def getUserId(request: AuthenticatedRequest[play.api.mvc.AnyContent], uid: Option[String]) = {
      uid match {
          case None => request.uid
          case Some(id) => id
        }
	}
}

class AuthenticatedRequest[A](val uid: String, request: Request[A]) extends WrappedRequest[A](request)

object AuthAction extends AuthAction {}
  
class AuthAction extends ActionBuilder[AuthenticatedRequest] {

    def invokeBlock[A](request: Request[A], block: (AuthenticatedRequest[A]) => Future[SimpleResult]) = {
      /*
    	val sessionControl = SessionController.get
    	sessionControl.getSessionIDFromCookie(request.cookies) match {
    		case None => Future.successful(Results.Unauthorized)
    		case Some(sessionId) => {
    			val uid = sessionControl.getUid(sessionId)
    			sessionControl.setUid(null, uid)
    			block(new AuthenticatedRequest(uid, request))
    		}
       }
       * */
      
       val uid = myMockSessionAdapter.getUid
       
	   block(new AuthenticatedRequest(uid, request))

    }
}

object AuthActionWithStats extends AuthAction {

    override def invokeBlock[A](request: Request[A], block: (AuthenticatedRequest[A]) => Future[SimpleResult]) = {
    	val path = request.path.replace("/", "_")
    	Stats.incr(path)
    	super.invokeBlock(request, block)
    }
}

object ActionWithStats extends ActionBuilder[Request] {

    def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[SimpleResult]) = {
    	val path = request.path.replace("/", "_")
    	Stats.incr(path)
    	block(request)
    }
}
