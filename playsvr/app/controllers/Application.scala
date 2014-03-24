package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current
import com.typesafe.plugin._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def mytest = Action {
	 
    //val html = views.html.shareInvite("nh")
    
    //val s = views.html.index2("Your new application is ready.")

	val receiver_email = "jzw899@gmail.com"
	val sender = "jack-cf"
	val sender_email = "<jzw899@email.com>"
	val number = 1
	val kooboodleWebSeverHost = "www.kooboodle.com"
	val cover_url = "http://development-kooboodlephoto-s3bucket.s3.amazonaws.com/ecd67a3b98b25747757527/base/201311/IMG_7380-8db126.JPG"
	val login_url = "http://dev.kooboodle.com/cf/user/register/invited?r=/cf/share/accept/a3e7941907c252841341361&utm_source=Website&utm_medium=email&utm_term=Shared&utm_content=User+Wants+To+Exchange&utm_campaign=Initiate+Exchange&existing_email=false&shared_email=jzw899@gmail.com"
 
    //Emailer.sendShareInvite(receiver_email, sender, sender_email, number, kooboodleWebSeverHost, cover_url, login_url)

    val html = apiv1.templates.email.html.shareInvite(receiver_email, sender, number, kooboodleWebSeverHost, cover_url, login_url)

    //Ok(views.html.shareInvite(receiver_email, sender, number, kooboodleWebSeverHost, cover_url, login_url))
  
    Ok("mytest")
    

  }
}
