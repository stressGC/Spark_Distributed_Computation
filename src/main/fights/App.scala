/**
  * @author Georges Cosson
  */
package fights

import org.apache.spark.graphx.{Graph, TripletFields}
import org.apache.spark.{SparkConf, SparkContext}

object App {
  // some imports
  val generator = new Generator()
  val mathHelper = new MathHelper()
  val graphHelper = new GraphHelper()

  /**
    * main function of our project
    * @param args
    */
  def main(args: Array[String]): Unit = {

    // Spark options
    val options = new SparkConf().setAppName("fight").setMaster("local[*]")
    val sparkContext = new SparkContext(options)
    sparkContext.setLogLevel("ERROR")

    // lets use our generator to get the graph
    val graph = generator.generateFightOne(sparkContext)

    // and launch the fight !!
    this.launchComputation(graph, sparkContext)
  }

  /**
    * launches the computations of the fight
    * @param graph
    * @param context
    */
  def launchComputation(graph: Graph[Entity, Int], context: SparkContext): Unit = {
    // lets keep track of our iteration number
    var iterationNumber = 0

    /**
      * this is our loop, its the core of the computation
      * @return
      */
    def fightIteration: Boolean = {
      var localGraph = graph // let's copy the base graph so we can modify it

      // while we have computation to do, checks stop condition later
      while (true) {
        iterationNumber += 1

        println("\nITERATION " + iterationNumber)

        /* https://spark.apache.org/docs/1.2.1/graphx-programming-guide.html#aggregate-messages-aggregatemessages */

        // lets grab all our messages !
        val allMessages = localGraph.aggregateMessages[(Entity, Entity, Long)](
          graphHelper.sendPosition,
          mathHelper.closestEntityLogic
        )

        // stop condition, if nothing has been done last iteration
        if (allMessages.isEmpty()) {
          println("\n*** FIGHT IS FINISHED ***")
          return false
        }

        /* https://spark.apache.org/docs/latest/graphx-programming-guide.html#join_operators */
        localGraph = localGraph.joinVertices(allMessages) {
          (id, src, currentMessage) => {
            val src = currentMessage._1 // source
            val dest = currentMessage._2 // destination (= closest entity)
            val distance = currentMessage._3 // distance between them

            // debug purpose
            println("=====================")
            println("*** " + src.getName().toUpperCase() + " ***")

            /* apply regeneration */
            src.applyRegen()

            // lets get our maximum range
            val maxAttackRange = src.getSpell().getRange()

            // if we can reach the opponent
            if (distance < maxAttackRange) {
              // then attack
              println(">>ATTACK {" + dest.getName() + "} WITH {" + src.getSpell().getName() + "} // RANGE{" + maxAttackRange + "}, DISTANCE{" + distance + "}")
              src.attack(dest)
            } else {
              // else move
              println(">>MOVE")
              src.moveInDirectionOf(dest)
            }
            src
          }
        }

        /* lets aggregate damage messages */
        val messageDamage = localGraph.aggregateMessages[(Entity, Int)](
          graphHelper.sendDamagesToDest,
          (acc, item) => {
            (acc._1, acc._2 + item._2)
          }
        )

        /* and join the vertices so we can apply the damages etc */
        localGraph = localGraph.joinVertices(messageDamage) {
          (id, src, message) => {
            val opponent = message._1
            val healthDifference = - message._2
            opponent.modifyHealth(healthDifference)
            opponent
          }
        }

      }

      return true
    }
    fightIteration
  }
}

