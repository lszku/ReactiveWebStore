import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.test._
import play.api.test.Helpers._

/**
  * Created by lukasz on 06.01.17.
  */
class RoutesTestSpec extends PlaySpec with OneAppPerTest{

  "Root Controller" should {

    "route to index page" in{
      val result = route(app, FakeRequest(GET,"/")).get
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include ("Your new application is ready")
    }


  }

}
