package fights

object App {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    val helper = new Helper()

    val entity = new Entity("entity1", 10, 10, 10, helper.getRandom(0, 1000), helper.getRandom(0, 1000), 20)
    val entity2 = new Entity("entity2", 10, 10, 10, helper.getRandom(0, 1000), helper.getRandom(0, 1000), 20)

    var i = 0

    while(i < 10) {
      entity.moveTo(entity2)
      i = i + 1
      println("-----------")
      println(entity)
      println(entity2)
    }


  }
}

