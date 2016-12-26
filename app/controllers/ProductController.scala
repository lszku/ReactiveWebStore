package controllers

import javax.inject.{Inject, Singleton}

import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.Controller
import services.IProductService
import models.Product

/**
  * Created by lukas on 12/25/2016.
  */

@Singleton
class ProductController @Inject()(val messagesApi: MessagesApi,
                                  val service: IProductService
                                 )
  extends Controller with I18nSupport {

  val productForm: Form[Product] = Form(
    mapping(
      "id" -> optional(longNumber),
      "name" -> nonEmptyText,
      "details" -> text,
      "price" -> bigDecimal
    )(models.Product.apply)(models.Product.unapply)
  )


  def index() = play.mvc.Results.TODO

  def blank() = play.mvc.Results.TODO

  def insert() = play.mvc.Results.TODO

  def update(id: Long) = play.mvc.Results.TODO

  def remove(id: Long) = play.mvc.Results.TODO

  def details(id: Long) = play.mvc.Results.TODO
}
