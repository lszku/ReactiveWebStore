package services

import javax.inject.Singleton

import models.Review

/**
  * Created by lukasz on 26.12.16.
  */
trait IReviewService extends BaseService[Review] {
  override def insert(a: Review): Long

  override def update(id: Long, a: Review): Boolean

  override def remove(id: Long): Boolean

  override def findById(id: Long): Option[Review]

  override def findAll(): Option[List[Review]]
}

@Singleton
class ReviewService extends IReviewService {

  def insert(review: Review): Long = {
    val id = idCounter.incrementAndGet();
    review.id = Some(id)
    inMemoryDB.put(id, review)
    id
  }

  private def validateId(id: Long): Unit = {
    val entry = inMemoryDB.get(id)
    if (entry == null) throw new RuntimeException("Could not find Review: " + id)
  }

  def update(id: Long, review: Review): Boolean = {
    validateId(id)
    review.id = Some(id)
    inMemoryDB.put(id, review)
    true
  }

  override def remove(id: Long): Boolean = {
    validateId(id)
    inMemoryDB.remove(id)
    true
  }

  def findById(id: Long): Option[Review] = {
    inMemoryDB.get(id)
  }

  def findAll(): Option[List[Review]] = {
    if (inMemoryDB.values.toList == null ||
      inMemoryDB.values.toList.size == 0) return None
    Some(inMemoryDB.values.toList)
  }
}
