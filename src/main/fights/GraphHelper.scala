/**
  * @author Georges Cosson
  */
package fights

import org.apache.spark.graphx.{Edge, EdgeContext}
import scala.collection.mutable.ArrayBuffer

/**
  * utility class used as a helper for graph transformations and actions
  */
class GraphHelper {
  val mathHelper = new MathHelper()
  /**
    * returns the entities as vertices
    * @param entities
    * @return
    */
  def getVerticesAsTuple(entities: ArrayBuffer[Entity]): ArrayBuffer[(Long, Entity)] = {
    val verticesWithIndex = entities.zipWithIndex
    // we swap the key/value for easier computation
    return verticesWithIndex.map(v => (v._2.toLong, v._1))
  }

  /**
    * we generate the edges, between the solar and each other entity
    * @param vertices
    * @param solarIndex index of the solar in the vertices array
    * @return
    */
  def generateEdges(vertices: ArrayBuffer[(Long, Entity)], solarIndex: Int = 0): ArrayBuffer[Edge[Int]] = {
    // will contain our edges
    val edges = new ArrayBuffer[Edge[Int]]()

    // for each vertice
    for(i <- 1 to vertices.length - 1) {
      val newEdges = new ArrayBuffer[Edge[Int]]()

      // append an edge between the vertice and the solar
      newEdges.append(Edge(vertices(solarIndex)._1.toLong, vertices(i)._1.toLong))

      // and between the solar and the vertice
      newEdges.append(Edge(vertices(i)._1.toLong, vertices(solarIndex)._1.toLong))
      edges.appendAll(newEdges)
    }

    return edges
  }

  /**
    * sends the distance between two entities to the dest
    * @param edgeC
    */
  def sendPosition(edgeC: EdgeContext[Entity, Int, (Entity, Entity, Long)]): Unit = {
    // get source & destination
    val src = edgeC.srcAttr
    val dest = edgeC.dstAttr

    /* if both are alive */
    val bothAlive = (dest.getHealth() > 0) && (src.getHealth() > 0)

    if (bothAlive) {
      // compute the distance between them
      val distance = mathHelper.distanceBetween(src, dest).toLong

      // send it
      edgeC.sendToDst((dest, src, distance))
    }
  }

  /**
    * sends the damages information to the destination
    * @param edgeC
    */
  def sendDamagesToDest(edgeC: EdgeContext[Entity, Int, (Entity, Int)]): Unit = {
    // get source & destination
    val src = edgeC.srcAttr
    val dest = edgeC.dstAttr

    /* if both still alive */
    val bothAlive = (src.getHealth() > 0) && (dest.getHealth() > 0)

    if (bothAlive){
      // get the damages the source deals
      val damages = src.getSpell().getDamages()

      // send it to the destination
      edgeC.sendToDst((edgeC.dstAttr, damages))
    }
  }
}
