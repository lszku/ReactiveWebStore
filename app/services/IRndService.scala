package services

import javax.inject.{Inject, Singleton}

import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}


/**
  * Created by lukasz on 06.01.17.
  */
trait IRndService {
  def next(): Double

  def call(): Future[Double]

  def rxScalaCall(): Observable[Double]

  def rxScalaCallBatch(): Observable[Double]
}

@Singleton
class RndService @Inject()(ws: WSClient) extends IRndService {

  import play.api.libs.concurrent.Execution.Implicits.defaultContext

  override def next(): Double = {
    val future = ws.url("http://localhost:9090/double").get.map {
      res => res.body.toDouble
    }
    Await.result(future, 5.seconds)
  }

  override def call(): Future[Double] = {
    val doubleFuture = ws.url("http://localhost:9090/double").get().map {
      x => x.body.toDouble
    }
    doubleFuture
  }

  override def rxScalaCall(): Observable[Double] = {
    val doubleFuture = ws.url("http://localhost:9090/double").get().map {
      x => x.body.toDouble
    }
    Observable.from(doubleFuture)
  }

  override def rxScalaCallBatch(): Observable[Double] = {
    val doubleInfiniteStreamSubject = PublishSubject.apply[Double]()
    val future = ws.url("http://localhost:9090/doubles/10")
      .get()
      .map { x =>
        Json.parse(x.body).as[List[Double]]
      }
    future.onComplete {
      case Success(l: List[Double]) => l.foreach { e =>
        doubleInfiniteStreamSubject.onNext(e)
      }
      case Failure(e: Exception) =>
        doubleInfiniteStreamSubject.onError(e)
    }

    val observableEven = Observable.create {
      doubleInfiniteStreamSubject.subscribe
    }.onErrorReturn { x => 2.0 }
      .flatMap { x =>
        Observable.from(Iterable.fill(1)(x + 10))
      }.flatMap { x => println("ODD: " + x); Observable.just(x) }
    val all = Observable.empty
      .merge(observableEven)
      .take(10)

    all
  }
}