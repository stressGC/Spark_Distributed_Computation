package fights

import fights.App.mathHelper
import org.apache.spark.graphx.{Edge, EdgeContext}

import scala.collection.mutable.ArrayBuffer

class GraphHelper {

  def getVerticesAsTuple(entities: ArrayBuffer[Entity]): ArrayBuffer[(Long, Entity)] = {
    val verticesWithIndex = entities.zipWithIndex
    return verticesWithIndex.map(v => (v._2.toLong, v._1))
  }

  def generateEdges(vertices: ArrayBuffer[(Long, Entity)], solarIndex: Int = 0): ArrayBuffer[Edge[Int]] = {
    val edges = new ArrayBuffer[Edge[Int]]()

    for(i <- 1 to vertices.length - 1) {
      val newEdges = new ArrayBuffer[Edge[Int]]()
      newEdges.append(Edge(vertices(solarIndex)._1.toLong, vertices(i)._1.toLong))
      newEdges.append(Edge(vertices(i)._1.toLong, vertices(solarIndex)._1.toLong))
      edges.appendAll(newEdges)
    }

    return edges
  }

  def sendPosition(context: EdgeContext[Entity, Int, (Entity, Entity, Long)]): Unit = {
    val src = context.srcAttr
    val dest = context.dstAttr
/*
    println("Source : " + src.getName())
    println("Destination : " + dest.getName())
    println("Distance : " + context.attr.toString)
    print("\n\n")
    println(context.srcId)
*/

    /* if both are alive */
    val bothAlive = (dest.getHealth() > 0) && (src.getHealth() > 0)

    if (bothAlive) {
      val distance = mathHelper.distanceBetween(src, dest).toLong
      context.sendToDst((dest, src, distance))
    }
  }

  def sendDamage(context: EdgeContext[Entity, Int, (Entity, Int)]): Unit = {
    val src = context.srcAttr
    val dest = context.dstAttr

    val bothAlive = (src.getHealth() > 0) && (dest.getHealth() > 0)

    if (bothAlive){
      val damages = src.getSpell().getDamages()
      context.sendToDst((context.dstAttr, damages))
    }
  }

}
