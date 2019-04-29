package fights
import java.util.concurrent.ThreadLocalRandom
import scala.math.sqrt
import scala.math.pow
class Helper {
  def getRandom(min: Int, max: Int) : Int = {
    val random: ThreadLocalRandom = ThreadLocalRandom.current()
    return  random.nextInt(min, max + 1)
  }

  def distanceBetween(entity1: Entity, entity2: Entity) : Float = {

    val xDiff = entity1.getX() - entity2.getX()
    val yDiff = entity1.getY() - entity2.getY()

    return sqrt(
      pow(xDiff, 2) + pow(yDiff, 2)
    ).toFloat
  }
}
