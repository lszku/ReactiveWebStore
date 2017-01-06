import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, OneServerPerSuite, PlaySpec}

/**
  * Created by lukasz on 06.01.17.
  */
class ProductControllerTestSpec extends PlaySpec with OneServerPerSuite
  with OneBrowserPerSuite with HtmlUnitFactory {

  "product controller" should {
    "insert a new product should be ok" in {
      goTo(s"http://localhost:$port/product/add")
      click on id("name")
      enter("Blue Ball")
      click on id("details")
      enter("Blue ball is a awesome and simple product")
      click on id("price")
      enter("15.44")
      submit()
    }
    "details from the product 1 should be ok" in {
      goTo(s"http://localhost:$port/product/details/1")
      textField("name").value mustBe "Blue Ball"
      textField("details").value mustBe "Blue ball is a awesome and simple product"
      textField("price").value mustBe "15.44"
    }
  }

}
