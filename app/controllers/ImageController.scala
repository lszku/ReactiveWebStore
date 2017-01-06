package controllers

import javax.inject.Inject

import models.Image
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.mvc.{Action, Controller}
import services.{IImageService, IProductService}

/**
  * Created by lukas on 12/25/2016.
  */
class ImageController @Inject()(val messagesApi: MessagesApi,
                                val productService: IProductService,
                                val imageService: IImageService
                               ) extends Controller with I18nSupport {

  val imageForm: Form[Image] = Form(
    mapping(
      "id" -> optional(longNumber),
      "productId" -> optional(longNumber),
      "url" -> text
    )(Image.apply)(Image.unapply)
  )

  def index() = Action { implicit request =>
    val images = imageService.findAll().getOrElse(Seq())
    Logger.info("Index called. Images: " + images)
    Ok(views.html.image_index(images))
  }

  def blank() = Action { implicit request =>
    Ok(views.html.image_details(None, imageForm, productService.findAllProducts()))
  }

  def insert() = Action { implicit request =>
    imageForm.bindFromRequest().fold(
      form => {
        BadRequest(views.html.image_details(None, form, productService.findAllProducts()))
      },
      image => {
        if (image.productId == null || image.productId.getOrElse(0) == 0) {
          Redirect(routes.ImageController.blank()).flashing("error" -> "Product ID cannot be null!")
        } else {
          if (image.url == null || "".equals(image.url))
//            image.url = "images/default_product.png"
            image.url = routes.Assets.versioned("images/default_product.png").url
          Logger.info(image.url)
          val id = imageService.insert(image)
          Redirect(routes.ImageController.index)
            .flashing("success" -> Messages("success.insert", id))
        }
      }
    )
  }

  def update(id: Long) = Action { implicit request =>
    Logger.info("updated called. id: " + id)
    imageForm.bindFromRequest.fold(
      form => {
        Ok(views.html.image_details(Some(id), form,
          null)).flashing("error" -> "Fix the errors!")
      },
      image => {
        imageService.update(id,image)
        Redirect(routes.ImageController.index).
          flashing("success" -> Messages("success.update",
            image.id))
      })
  }

  def remove(id: Long) = Action {
    imageService.findById(id).map { image =>
      imageService.remove(id)
      Redirect(routes.ImageController.index).flashing("success"
        -> Messages("success.delete", image.id))
    }.getOrElse(NotFound)
  }

  def details(id: Long) = Action { implicit request =>
    val image = imageService.findById(id).get
    Ok(views.html.image_details(Some(id),imageForm.fill(image),productService.findAllProducts()))

  }
}
