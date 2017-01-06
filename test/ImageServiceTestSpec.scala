import org.scalatestplus.play.PlaySpec
import services.{IImageService, ImageService}

/**
  * Created by lukasz on 06.01.17.
  */
class ImageServiceTestSpec extends PlaySpec {
  "ImageService" must {
    val service: IImageService = new ImageService

    "insert a image properly" in {
      val image = models.Image(Some(1), Some(1), "http://www.google.com/myimage")
      val result = service insert image
      result mustBe 1
    }

    "update a image" in {
      val image = models.Image(Some(2), Some(1),
        "http://www.google.com.br/myimage")
      service.update(1, image)
    }

    "not update because does not exist" in {
      intercept[RuntimeException] {
        service.update(333, null)
      }
    }

    "find the image1" in {
      val image = service.findById(1)
      image.get.id mustBe Some(1)
      image.get.productId mustBe Some(1)
      image.get.url mustBe "http://www.google.com.br/myimage"
    }

    "find all" in {
      val reviews = service.findAll()
      reviews.get.length mustBe 1
      reviews.get.head.id mustBe Some(1)
      reviews.get.head.productId mustBe Some(1)
      reviews.get.head.url mustBe "http://www.google.com.br/myimage"
    }

    "remove 1 image" in {
      val image = service.remove(1)
      image mustBe true
      val oldImage = service.findById(1)
      oldImage mustBe None
    }

    "not remove because does not exist" in {
      intercept[RuntimeException]{
        service.remove(-1)
      }
    }

  }

}
