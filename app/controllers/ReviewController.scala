package controllers

import javax.inject.Inject

import models.Review
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.mvc.{Action, Controller}
import services.{IProductService, IReviewService}

/**
  * Created by lukas on 12/25/2016.
  */
class ReviewController @Inject()
(val messagesApi: MessagesApi,
 val productService: IProductService,
 val service: IReviewService)
  extends Controller with I18nSupport {

  val reviewForm: Form[Review] = Form(
    mapping(
      "id" -> optional(longNumber),
      "productId" -> optional(longNumber),
      "author" -> nonEmptyText,
      "comment" -> nonEmptyText
    )(Review.apply)(Review.unapply)
  )

  def index() = Action { implicit request =>
    val review = service.findAll().getOrElse(Seq())
    Logger.info("index called. Reviews: " + review)
    Ok(views.html.review_index(review))
  }

  def blank() = Action { implicit request =>
    Logger.info("Blank called")
    Ok(views.html.review_details(None, reviewForm, productService.findAllProducts()))
  }

  def insert() = Action { implicit request =>
    Logger.info("insert called")
    reviewForm.bindFromRequest().fold(
      form => {
        BadRequest(views.html.review_details(None, form, productService.findAllProducts()))
      },
      review => {
        if (review.productId == null || review.productId.getOrElse(0) == 0) {
          Redirect(routes.ReviewController.blank()).flashing("error" -> "Product Id cannot be null")
        } else {
          Logger.info("Review: " + review)
          if (review.productId == null || review.productId.getOrElse(0) == 0)
            throw new IllegalArgumentException("Product Id cannot be null")
          val id = service.insert(review)
          Redirect(routes.ReviewController.index())
            .flashing("success" -> Messages("success.insert", id))
        }
      })
  }

  def details(id: Long) = Action { implicit request =>
    Logger.info("details called. id: " + id)
    val review = service.findById(id).get
    Ok(views.html.review_details(Some(id),
      reviewForm.fill(review), productService.findAllProducts()))
  }

  def update(id: Long) = Action { implicit request =>
    reviewForm.bindFromRequest().fold(
      form => {
        Ok(views.html.review_details(Some(id), form, productService.findAllProducts()))
          .flashing("error" -> "Fix the errors!")
      },
      review => {
        service.update(id, review)
        Redirect(routes.ReviewController.index())
          .flashing("success" -> Messages("success.update", review.productId))
      })
  }

  def remove(id: Long) = Action {
    service.findById(id).map { review =>
      service.remove(id)
      Redirect(routes.ReviewController.index())
        .flashing("success" -> Messages("success.delete", review.productId))
    }.getOrElse(NotFound)
  }
}
