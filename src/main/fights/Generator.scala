package fights

import org.apache.spark.SparkContext
import org.apache.spark.graphx.{Edge, Graph}

import scala.collection.mutable.ArrayBuffer

class Generator {
  val fightGraph = new GraphHelper()
  val mathHelper = new MathHelper()

  def generateFightOne(sc: SparkContext): Graph[Entity, Int] = {

    //Definition des sommets
    val vertices = this.getFightOneEntities()
    val verticesAsTuples = fightGraph.getVerticesAsTuple(vertices)

    //verticesAsTuples.foreach(x => println("\nID = " + x._1 + ", " + x._2.toString))

    //Definition des arretes
    val edges = fightGraph.generateEdges(verticesAsTuples)
    //println("edges :")
    //edges.foreach(x => println(x.toString))

    return Graph(sc.makeRDD(verticesAsTuples), sc.makeRDD(edges))

  }

  def getFightOneEntities() : ArrayBuffer[Entity] = {
    /*
      1 Solar
      vs
      9 Worgs Rider
      4 Barbares Orcs
      1 Warlord

       name healPoint healPointMax armor weaponArray x y peed regeneration
     */

    var entities = new ArrayBuffer[Entity]()

    /* 1x Solar */
    val greatSword = new Spell(name = "Great Sword", range = 30, min = 21, max = 24)
    val solar = new Entity(name = "Solar", health = 1364, armor = 44, regen = 15, speed = 50, spell = greatSword, coordX = mathHelper.getRandom(0, 500), coordY = mathHelper.getRandom(0, 500))
    entities += solar

    /* 9x Worgs Rider */
    val battleAxe = new Spell(name = "Battle Axe", range = 15 , min = 3, max = 11)
    (1 to 9) foreach (x => {
      entities += new Entity(name = "WorgRider #" + x, health = 13, armor = 18, regen = 0, speed = 50, spell = battleAxe, coordX = mathHelper.getRandom(0, 500), coordY = mathHelper.getRandom(0, 500))
    })

    /* 4x Barbares Orcs */
    val greatAxe = new Spell(name = "Great Axe", range = 10 , min = 11, max = 23)
    (1 to 9) foreach (x => {
      entities += new Entity(name = "Barbares Orcs #" + x, health = 42, armor = 15, regen = 0, speed = 30, spell = greatAxe, coordX = mathHelper.getRandom(0, 500), coordY = mathHelper.getRandom(0, 500))
    })

    /* Warlord */
/*

*/
    return entities
  }
}
