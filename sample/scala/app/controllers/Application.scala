package controllers

import play.api._
import play.api.mvc._
import play.api.libs.iteratee.Enumerator
import com.degiske.play2.chameleon.Chameleon

object Application extends Controller {

  def index(theme : String) = Action {
    val param = Array[Object]("Your theme is " + theme)
    SimpleResult(
      header = ResponseHeader(200, Map(CONTENT_TYPE -> "text/html")),
      body = Enumerator(Chameleon.render("views.html."+ theme + ".index", param).toString().getBytes())
    )
  }

}