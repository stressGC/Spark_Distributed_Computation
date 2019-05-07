/**
  * @author Georges Cosson
  */
package fights

import java.util.concurrent.ThreadLocalRandom
import scala.collection.mutable.ArrayBuffer
import scala.math.sqrt
import scala.math.pow

/**
  * utility class used as a helper for math computation etc
  */
class MathHelper extends Serializable {

  /**
    * returns a random int between two numbers
    * @param min
    * @param max
    * @return
    */
  def getRandom(min: Int, max: Int) : Int = {
    val random: ThreadLocalRandom = ThreadLocalRandom.current()
    return  random.nextInt(min, max + 1)
  }

  /**
    * computes the distance between two entities
    * @param entity1
    * @param entity2
    * @return
    */
  def distanceBetween(entity1: Entity, entity2: Entity) : Float = {
    // compute the difference on both axis
    val xDiff = entity1.getX() - entity2.getX()
    val yDiff = entity1.getY() - entity2.getY()

    // euclidean distance
    return sqrt(
      pow(xDiff, 2) + pow(yDiff, 2)
    ).toFloat
  }

  /**
    * zips the entity array with index, to be used later
    * @param entities
    * @return
    */
  def getEntitiesWithIndex(entities: ArrayBuffer[Entity]) : ArrayBuffer[(Int, Entity)] = {
    /* taken from https://alvinalexander.com/scala/how-to-use-zipwithindex-create-for-loop-counters-scala-cookbook */
    return entities.zipWithIndex.map{ case(entity , count) => (count, entity) }
  }

  /**
    * utility function used by the aggregateMessage to determine which entity is the closest
    * @param entity
    * @param entity2
    * @return
    */
  def closestEntityLogic(entity: (Entity, Entity, Long), entity2: (Entity, Entity, Long)): (Entity, Entity, Long) = {
    // the third accessor is the distance
    if (entity._3 < entity2._3) return entity2
    return entity2
  }
}
