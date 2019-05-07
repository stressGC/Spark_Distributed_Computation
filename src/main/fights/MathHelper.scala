package fights
import java.util.concurrent.ThreadLocalRandom

import scala.collection.mutable.ArrayBuffer
import scala.math.sqrt
import scala.math.pow
class MathHelper extends Serializable {

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

  def getEntitiesWithIndex(entities: ArrayBuffer[Entity]) : ArrayBuffer[(Int, Entity)] = {
    /* taken from https://alvinalexander.com/scala/how-to-use-zipwithindex-create-for-loop-counters-scala-cookbook */
    return entities.zipWithIndex.map{ case(entity , count) => (count, entity) }
  }

  def closestEntityLogic(entity: (Entity, Entity, Long), entity2: (Entity, Entity, Long)): (Entity, Entity, Long) = {
    if (entity._3 < entity2._3) return entity2
    return entity2
  }
}
