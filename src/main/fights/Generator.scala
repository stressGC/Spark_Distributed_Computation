/**
  * @author Georges Cosson
  */
package fights

import org.apache.spark.SparkContext
import org.apache.spark.graphx.{Edge, Graph}
import scala.collection.mutable.ArrayBuffer

/**
  * utility class used to generate various objects
  */
class Generator {
  // some helpers
  val fightGraph = new GraphHelper()
  val mathHelper = new MathHelper()

  /**
    * returns a graph representing the fight #1
    * @param sc SparkContext
    * @return
    */
  def generateFightOne(sc: SparkContext): Graph[Entity, Int] = {

    // lets get our vertices
    val vertices = this.getFightOneEntities()

    // convert them as tuples
    val verticesAsTuples = fightGraph.getVerticesAsTuple(vertices)


    // lets get our edges
    val edges = fightGraph.generateEdges(verticesAsTuples)

    // finally return the Graph made from our vertices and edges
    return Graph(sc.makeRDD(verticesAsTuples), sc.makeRDD(edges))

  }

  /**
    * generates all the entities of the first fight
    * @return ArrayBuffer of Entities
    */
  def getFightOneEntities() : ArrayBuffer[Entity] = {
    /*
      1 Solar : https://pathfinderwiki.com/wiki/Solar
      vs
      9 Worgs Rider
      4 Barbares Orcs
      1 Warlord
     */

    // will contain our entities
    var entities = new ArrayBuffer[Entity]()

    /* 1x Solar */
    val lumenSword = new Spell(name = "Lumen Sword", range = 30, min = 21, max = 24)
    val solar = new Entity(name = "Solar", health = 364, armor = 44, regen = 15, speed = 50, spell = lumenSword, coordX = mathHelper.getRandom(0, 500), coordY = mathHelper.getRandom(0, 500))
    entities += solar

    /* 9x Worgs Rider */
    val battleAxe = new Spell(name = "Battle Axe", range = 15 , min = 3, max = 11)
    (1 to 9) foreach (x => {
      entities += new Entity(name = "WorgRider #" + x, health = 13, armor = 18, regen = 0, speed = 50, spell = battleAxe, coordX = mathHelper.getRandom(0, 500), coordY = mathHelper.getRandom(0, 500))
    })

    /* 4x Barbares Orcs */
    val bigAxe = new Spell(name = "Big Axe", range = 10 , min = 11, max = 23)
    (1 to 4) foreach (x => {
      entities += new Entity(name = "Barbares Orcs #" + x, health = 42, armor = 15, regen = 0, speed = 30, spell = bigAxe, coordX = mathHelper.getRandom(0, 500), coordY = mathHelper.getRandom(0, 500))
    })

    /* 1x Warlord */
    val viciousFail = new Spell(name = "Viscious Fail", range = 10, min = 9, max = 18)
    val warlord = new Entity(name = "Warlord", health = 141, armor = 27, regen = 0, speed = 30, spell = viciousFail, coordX = mathHelper.getRandom(0, 500), coordY = mathHelper.getRandom(0, 500))
    entities += warlord

    // return all those entities !
    return entities
  }
}
