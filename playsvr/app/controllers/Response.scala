package controllers

import play.api.libs.json.JsValue
import play.api.libs.json._

case class Response(Code: Int, Result: JsValue)	

case class ApiResponse(Code: Int, Message: String, Result: JsValue)

object ApiResponse {
	implicit val read = Json.reads[ApiResponse]
	implicit val write = Json.writes[ApiResponse]
}
