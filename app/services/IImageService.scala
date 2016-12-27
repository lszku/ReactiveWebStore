package services

import javax.inject.Singleton

import models.Image

/**
  * Created by lukasz on 26.12.16.
  */
trait IImageService extends BaseService[Image] {
  override def insert(a: Image): Long

  override def update(id: Long, a: Image): Boolean

  override def remove(id: Long): Boolean

  override def findById(id: Long): Option[Image]

  override def findAll(): Option[List[Image]]
}

@Singleton
class ImageService extends IImageService {
  override def insert(image: Image): Long = {
    val id = idCounter.incrementAndGet()
    image.id = Some(id)
    inMemoryDB.put(id, image)
    id
  }

  def validateId(id: Long) = {
    val entry = inMemoryDB.get(id)
    if (entry == null) throw new RuntimeException("Couldn't find image: " + id)
  }

  override def update(id: Long, image: Image): Boolean = {
    validateId(id)
    image.id = Some(id)
    inMemoryDB.put(id, image)
    true
  }

  override def remove(id: Long): Boolean = {
    validateId(id)
    inMemoryDB.remove(id)
    true
  }

  override def findById(id: Long): Option[Image] = {
    inMemoryDB.get(id)
  }

  override def findAll(): Option[List[Image]] = {
    if (inMemoryDB.values.toList == null || inMemoryDB.values.toList.size == 0) None
    else Some(inMemoryDB.values.toList)

  }
}