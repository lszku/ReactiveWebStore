package services


import javax.inject.{Inject, Singleton}

import rx.lang.scala.Observable
import rx.lang.scala.schedulers.IOScheduler
import rx.lang.scala.subjects.PublishSubject

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random


/**
  * Created by lukasz on 27.12.16.
  */
trait IPriceService {
  def generatePrices: Observable[Double]
}

@Singleton
class PriceService @Inject()(implicit executionContext: ExecutionContext) extends IPriceService {
  var doubleInfiniteStreamSubject = PublishSubject.apply[Double]
  Future {
    Stream.continually(Random.nextDouble() * 1000) foreach {
      x =>
        Thread.sleep(1)
        doubleInfiniteStreamSubject.onNext(x)
    }
  }

  override def generatePrices: Observable[Double] = {
    val observableEven = Observable.create(
      doubleInfiniteStreamSubject.subscribe)
      .subscribeOn(IOScheduler())
      .flatMap {
        x => Observable.from(Iterable.fill(1)(x + 10))
      }.filter(x => x.toInt % 2 == 0)

    val observableOdd = Observable.create(
      doubleInfiniteStreamSubject.subscribe)
      .subscribeOn(IOScheduler())
      .flatMap {
        x => Observable.from(Iterable.fill(1)(x + 10))
      }.filter(x => x.toInt % 2 != 0)

    val mergeObservable = Observable
      .empty
      .subscribeOn(IOScheduler())
      .merge(observableEven)
      .merge(observableOdd)
      .take(10)
      .foldLeft(0.0)(_ + _)
      .flatMap(x => Observable.just(x - (x * 0.9)))

    mergeObservable
  }
}
