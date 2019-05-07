package fights

import org.apache.spark.graphx.{Graph, TripletFields}
import org.apache.spark.{SparkConf, SparkContext}


object App {
  val generator = new Generator()
  val mathHelper = new MathHelper()
  val graphHelper = new GraphHelper()

  def main(args: Array[String]): Unit = {

    val options = new SparkConf().setAppName("EXO2").setMaster("local[*]")
    val sparkContext = new SparkContext(options)
    sparkContext.setLogLevel("ERROR")

    val graph = generator.generateFightOne(sparkContext)

    this.launchComputation(graph, 100, sparkContext)
  }

  def launchComputation(graph: Graph[Entity, Int], maxIterations: Int = 50, context: SparkContext): Unit = {
    var counter = 0
    val fields = new TripletFields(true, false, false) //join strategy

    def fightIteration: Boolean = {
      var localGraph = graph // let's copy the base graph so we can modify it
      while (true) {
        counter += 1

        if (counter >= maxIterations) return false;

        println("\nITERATION " + counter)

        /* https://spark.apache.org/docs/1.2.1/graphx-programming-guide.html#aggregate-messages-aggregatemessages */

        val messages = localGraph.aggregateMessages[(Entity, Entity, Long)](
          graphHelper.sendPosition,
          mathHelper.closestEntityLogic
          //fields //use an optimized join strategy (we don't need the edge attribute)
        )

        if (messages.isEmpty()) {
          println("\n\n*** FIGHT IS FINISHED ***")
          return false
        }

        /* https://spark.apache.org/docs/latest/graphx-programming-guide.html#join_operators */
        localGraph = localGraph.joinVertices(messages) {
          (vertexID, pSrc, message) => {
            val src = message._1 // source
            val dest = message._2 // cible la plus proche
            val distance = message._3 // distance entre les deux
            println("=====================")
            println("*** " + src.getName().toUpperCase() + " ***")
            /*
          println("ID = " + vertexID.toString)
          println("source :" + pSrc.getName())
          println("source : " + src.getName() + ", dest = " + dest.getName() + ", distance = " + distance)
  */
            val maxAttackRange = src.getSpell().getRange()
            //println("ranges : " + maxAttackRange + " / " + distance + " ")

            /* apply regeneration */
            src.applyRegen()

            if (distance < maxAttackRange) {
              // attack
              println(">>ATTACK {" + dest.getName() + "} WITH {" + src.getSpell().getName() + "} // RANGE{" + maxAttackRange + "}, DISTANCE{" + distance + "}")
              src.attack(dest)
            } else {
              // move
              println(">>MOVE")
              src.moveInDirectionOf(dest)
            }
            src
          }
        }

        val messageDamage = localGraph.aggregateMessages[(Entity, Int)](
          graphHelper.sendDamagesToDest,
          (a, b) => {
            (a._1, a._2 + b._2)
          }
        )

        localGraph = localGraph.joinVertices(messageDamage) {
          (VertexID, psrc, msgrecu) => {
            msgrecu._1.modifyHealth(-msgrecu._2)
            msgrecu._1
          }
        }

      }

      return true
    }

    fightIteration
  }




}

