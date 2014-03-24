package controllers

import play.api.Play.current
import com.typesafe.plugin._

object Emailer {
  
  def sendHtmlEmail(receiver_email: String, sender: String, sender_email: String, subject: String, htmlBody: String) = {

    val mail = use[MailerPlugin].email
    mail.setSubject(subject)
	//mail.setRecipient("Jack Z.W. <jzw899@email.com>","jzw8799@email.com")
	mail.setRecipient(receiver_email)
	//or use a list
	//mail.setBcc(List("Dummy <example@example.org>", "Dummy2 <example@example.org>"):_*)
	//mail.setFrom("Jack Z.W. <jzw899@email.com>")
	mail.setFrom(sender_email)
	//sends html
	//mail.sendHtml("<html>html, mytest</html>" )
	mail.sendHtml(htmlBody)
	//sends text/text
	//mail.send( "text" )
	//sends both text and html
	//mail.send( "text", "<html>html</html>")
  }
  
  def sendShareInvite(receiver_email: String, sender: String, sender_email: String, number: Int, kooboodleWebSeverHost: String, cover_url: String, login_url: String) = {
    val html = apiv1.templates.email.html.shareInvite(receiver_email, sender, number, kooboodleWebSeverHost, cover_url, login_url)

    this.sendHtmlEmail(receiver_email, sender, sender_email, "Kooboodle", html.toString)
  }

}