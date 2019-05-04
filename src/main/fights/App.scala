package fights

import scala.collection.mutable.ArrayBuffer

object App {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    val helper = new Helper()

    val spellA = new Spell("spellA", 100, 5, 15)
    val spellB = new Spell("spellB", 100, 5, 22)

    val entity = new Entity("entity1", 15, 10, 10, helper.getRandom(0, 1000), helper.getRandom(0, 1000), 20, spellA)
    val entity2 = new Entity("entity2", 15, 10, 10, helper.getRandom(0, 1000), helper.getRandom(0, 1000), 20, spellB)


    var allEntities = new ArrayBuffer[Entity]()
    allEntities += entity
    allEntities += entity2


    /*
    val myVertices = helper.getEntitiesWithIndex(allEntities)
    myVertices.foreach(x => println("\nID = " + x._1 + ", " + x._2.toString))
  */

    var i = 0
    while(i < 10) {
      entity.attack(entity2)
      entity2.attack(entity)
      i = i + 1
      println("-----------")
      println(entity)
      println(entity2)
    }
  }
}

