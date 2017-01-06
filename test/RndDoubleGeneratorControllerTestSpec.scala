import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, OneServerPerSuite, PlaySpec}
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSClient

import scala.concurrent.Await
import scala.concurrent.duration.DurationLong

/**
  * Created by lukasz on 06.01.17.
  */
class RndDoubleGeneratorControllerTestSpec extends PlaySpec with OneServerPerSuite
  with OneBrowserPerSuite with HtmlUnitFactory {

  import play.api.libs.concurrent.Execution.Implicits.defaultContext
  val injector: Injector = new GuiceApplicationBuilder().injector()
  val ws: WSClient = injector.instanceOf(classOf[WSClient])

  "assuming ng-microservice is down rx number should be " must {
    val future = ws.url(s"http://localhost:9000/rnd/rxbat")
      .get().map(res=> res.body)
    val response = Await.result(future, 15.seconds)
    response mustBe "12.0"
  }


}
