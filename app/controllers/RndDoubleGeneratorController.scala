package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}
import services.IRndService

/**
  * Created by lukasz on 29.12.16.
  */

@Singleton
class RndDoubleGeneratorController @Inject()(service: IRndService) extends Controller{


  def rndDouble() = Action{ implicit request =>
    Ok(service.next().toString)

  }

  def rndCall() = play.mvc.Results.TODO

  def rxCall() = play.mvc.Results.TODO

  def rxScalaCallBatch() = Action { implicit request =>
    Ok(service.rxScalaCallBatch().toBlocking.first.toString)
  }
}
