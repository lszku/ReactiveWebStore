package services

/**
  * Created by lukas on 12/25/2016.
  */
import javax.inject.Singleton

import models.Product

trait IProductService extends BaseService[Product]{
  def insert(product: Product):Long
  def update(id:Long, product: Product):Boolean
  def remove(id:Long):Boolean
  def findById(id:Long):Option[Product]
  def findAll():Option[List[Product]]
  def findAllProducts():Seq[(String,String)]

}

@Singleton
class ProductService extends IProductService{

  override def insert(product: Product):Long = {
    val id = idCounter.incrementAndGet()
    product.id = Some(id)
    inMemoryDB.put(id,product)
    id
  }

  override def update(id: Long, product: Product) = ???

  override def remove(id: Long) = ???

  override def findById(id: Long) = ???

  override def findAll() = ???

  override def findAllProducts() = ???
}
