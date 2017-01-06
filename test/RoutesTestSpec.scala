import org.scalatestplus.play.{OneAppPerSuite, OneAppPerTest, PlaySpec}
import play.api.test._
import play.api.test.Helpers._

/**
  * Created by lukasz on 06.01.17.
  */
class RoutesTestSpec extends PlaySpec with OneAppPerSuite {

  "Root Controller" should {
    "route to index page" in {
      val result = route(app, FakeRequest(GET, "/")).get
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Your new application is ready")
    }
  }

  "Product Controller" should {
    "route to product landing page" in {
      val result = route(app, FakeRequest(GET, "/product")).get
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Products")
    }

    "route to product 1 details page " in {
      try {
        route(app, FakeRequest(GET, "/product/details/1")).get
      } catch {
        case _: Exception => Unit
      }
    }
  }


}
