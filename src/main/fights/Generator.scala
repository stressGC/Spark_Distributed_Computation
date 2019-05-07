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
    val spellA = new Spell("spellA", 100, 5, 15)
    val spellB = new Spell("spellB", 100, 18, 28)

    val entity = new Entity("entity1", 10, 10, 50, mathHelper.getRandom(0, 400), mathHelper.getRandom(0, 400), 20, spellA)
    val entity3 = new Entity("entity3", 10, 10, 50, mathHelper.getRandom(0, 400), mathHelper.getRandom(0, 400), 20, spellA)
    val entity2 = new Entity("entity2", 12, 10, 80, mathHelper.getRandom(0, 400), mathHelper.getRandom(0, 400), 20, spellB)

    var entities = new ArrayBuffer[Entity]()

    entities += entity
    entities += entity2
    entities += entity3

    return entities
  }
}
