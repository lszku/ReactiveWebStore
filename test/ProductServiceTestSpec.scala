import org.scalatestplus.play.PlaySpec
import services.{IProductService, ProductService}

/**
  * Created by lukasz on 06.01.17.
  */
class ProductServiceTestSpec extends PlaySpec{



  "Product Service " must {
    val service: IProductService = new ProductService

    "insert a product properly" in {
      val product = models.Product(Some(1), "Ball","Awesome basketball", 19.50)
      service.insert(product)
    }

    "update a product" in {
      val product = models.Product(Some(1), "Blue Ball","Awesome blue basketball", 19.99)
      service.update(1, product)
    }

    "not update if does not exist" in {
      intercept[RuntimeException]{
        service.update(333, null)
      }
    }

    "find the product 1" in {
      val product  = service.findAll()
      product.get.length mustBe 1
      product.get.head.id mustBe Some(1)
      product.get.head.name mustBe "Blue Ball"
      product.get.head.price mustBe 19.99
    }

    "remove 1 products " in {
      val product: Boolean = service.remove(1)
      product mustBe true
      val oldProduct = service findById 1
      oldProduct mustBe None
    }

    "not remove because does not exist" in {
      intercept[RuntimeException]{
        service.remove(1)
      }
    }

  }
}
