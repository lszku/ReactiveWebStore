import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.Assert._
import org.scalatest.junit.AssertionsForJUnit
import org.scalatestplus.play.PlaySpec
import services.ProductService

/**
  * Created by lukasz on 06.01.17.
  */

@RunWith(classOf[Suite])
@Suite.SuiteClasses(Array(classOf[JunitSimpleTest]))
class JunitSimpleSuiteTest
class JunitSimpleTest extends PlaySpec with AssertionsForJUnit {
  @Test def testBaseService(): Unit ={
    val s = new ProductService
    val result = s.findAll()
    assertEquals(None, result)
    assertTrue(result != null)
  }
}
