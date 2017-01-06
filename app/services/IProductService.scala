package services

/**
  * Created by lukas on 12/25/2016.
  */

import javax.inject.Singleton

import models.Product

trait IProductService extends BaseService[Product] {
  override def insert(product: Product): Long

  override def update(id: Long, product: Product): Boolean

  override def remove(id: Long): Boolean

  override def findById(id: Long): Option[Product]

  override def findAll(): Option[List[Product]]

  def findAllProducts(): Seq[(String, String)]

}

@Singleton
class ProductService extends IProductService {

  override def insert(product: Product): Long = {
    val id = idCounter.incrementAndGet()
    product.id = Some(id)
    inMemoryDB.put(id, product)
    id
  }

  private def validateId(id: Long): Unit = {
    val entry = inMemoryDB.get(id)
    if (entry.isEmpty || entry == null) throw new RuntimeException("Could not find Product: " + id)
  }

  override def update(id: Long, product: Product): Boolean = {
    validateId(id)
    product.id = Some(id)
    inMemoryDB.put(id, product)
    true
  }

  override def remove(id: Long): Boolean = {
    validateId(id)
    inMemoryDB.remove(id)
    true
  }

  override def findById(id: Long): Option[Product] = {
    inMemoryDB.get(id)
  }

  override def findAll() = {
    if (inMemoryDB.values == Nil || inMemoryDB.values.toList.isEmpty) None
    else
      Some(inMemoryDB.values.toList)
  }

  override def findAllProducts(): Seq[(String, String)] = {
    val products: Seq[(String, String)] = this
      .findAll()
      .getOrElse(List(Product(Some(0), "", "", 0)))
      .map { product => (product.id.get.toString, product.name) }
    products
  }
}
