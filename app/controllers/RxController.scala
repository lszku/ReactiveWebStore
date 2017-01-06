package controllers

import javax.inject.{Inject, Singleton}

import play.api.Logger
import play.api.mvc.{Action, Controller}
import rx.lang.scala.Observable
import rx.lang.scala.schedulers.IOScheduler
import services.IPriceService

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by lukasz on 27.12.16.
  */

@Singleton
class RxController @Inject()(priceService: IPriceService,
                             implicit val ex: ExecutionContext)
  extends Controller {

  def prices() = Action { implicit request =>
    val sourceObservable: Observable[Double] = priceService.generatePrices
    val rxResult = Observable.create(sourceObservable.subscribe)
      .subscribeOn(IOScheduler())
      .take(1)
      .flatMap{x => Logger.info("logged from prices method: " + x.toString); Observable.just(x)}
      .toBlocking
      .first

    Ok(" RxScala price suggested: " + rxResult)
  }

  def pricesAsync() = Action.async { implicit request =>
    val sourceObservable=priceService.generatePrices
    val rxResult = Observable.create(sourceObservable.subscribe)
      .subscribeOn(IOScheduler())
      .take(1)
      .flatMap{x=> Logger.info(x.toString); Observable.just(x)}
      .toBlocking
      .first

    Future {
      Ok(" RxScala async price suggested: " + rxResult)
    }

  }
}
