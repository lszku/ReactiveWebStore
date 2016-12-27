package models

/**
  * Created by lukas on 12/25/2016.
  */
case class Product(
                    var id: Option[Long],
                    var name: String,
                    var details: String,
                    var price: BigDecimal
                  ) {
  override def toString = {
    "Product { id: " + id.getOrElse(0) + ", name: " + name +
      ", details: " + details + ", price: " + price + "}"
  }
}


case class Review(
                   var id: Option[Long],
                   var productId: Option[Long],
                   var author: String,
                   var comment: String
                 ) {
  override def toString = {
    s"Review { id: $id, productId: ${productId.getOrElse(0)}, author: $author" +
      s", comment: $comment}"
  }
}

case class Image(
                  var id: Option[Long],
                  var productId: Option[Long],
                  var url: String
                ) {
  override def toString = {
    s"Image { productId: ${productId.getOrElse(0)}, url: $url}"
  }
}
