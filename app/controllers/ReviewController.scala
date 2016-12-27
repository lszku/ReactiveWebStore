package controllers

import javax.inject.Inject

import play.api.i18n.MessagesApi
import play.api.mvc.Controller
import services.IProductService

/**
  * Created by lukas on 12/25/2016.
  */
class ReviewController @Inject()
(val messagesApi: MessagesApi,
 val productService: IProductService)
  extends Controller {


  def index() = play.mvc.Results.TODO

  def blank() = play.mvc.Results.TODO

  def insert() = play.mvc.Results.TODO

  def update(id: Long) = play.mvc.Results.TODO

  def remove(id: Long) = play.mvc.Results.TODO

  def details(id: Long) = play.mvc.Results.TODO
}
