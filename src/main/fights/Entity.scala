/**
  * @author Georges Cosson
  */
package fights

/**
  * class representing an entity
  * @param name
  * @param armor
  * @param regen
  * @param health
  * @param coordX
  * @param coordY
  * @param speed
  * @param spell
  */
class Entity(val name: String, val armor: Int, val regen: Int, var health: Int, var coordX: Float, var coordY: Float, val speed: Int, val spell: Spell) extends Serializable {
  /* attributes */
  private val helper = new MathHelper()
  private val maxHealth = this.health

  /* accessors */
  def getSpell() : Spell = {
    return this.spell
  }
  def getName() : String = {
    return this.name
  }

  def getX(): Float = {
    return this.coordX
  }

  def getY(): Float = {
    return this.coordY
  }

  def getHealth() : Int = {
    return this.health
  }

  def getArmor() : Int = {
    return this.armor
  }

  /**
    * modifies the instance health
    * @param difference
    */
  def modifyHealth(difference: Int) : Unit = {
    // debug purpose
    if(difference < 0) {
      println(">>" + this.name + " LOST {" + difference + "} HP, FROM {" + this.health + "} TO {" + (this.health + difference) + "}")
    }

    // debug purpose
    if(this.health + difference <= 0) {
      println(">>" + this.name + " IS DEAD")
    }

    // lets add the difference
    this.health += difference
  }

  /**
    * function called each iteration to apply the regeneration to the entity
    */
  def applyRegen() : Unit = {
    // debug purpose
    var msg = ""

    // check if max health is reached
    if(this.getHealth() + this.regen > this.maxHealth) {
      msg = (this.maxHealth - this.getHealth()).toString
      this.health = this.maxHealth
    } else {
      // else apply the regen
      this.modifyHealth(this.regen)
      msg = this.regen.toString
    }
    println(">>REGEN {+" + msg + "}, NOW {" + this.health + "}")
  }

  /**
    * function called when the entity attacks an opponent
    * @param opponent
    */
  def attack(opponent: Entity) : Unit = {
    // lets get the damage of our spell
    val attackPower = this.spell.getDamages();

    // lets get opponent armor
    val opponentArmor = opponent.getArmor();

    // if we deal more than opponent has armor
    if (attackPower > opponentArmor) {
      println(">>(OK) : DMG{" + attackPower + "}, ARMOR{" + opponentArmor + "}")

      // apply damages
      opponent.modifyHealth(-attackPower)
    } else {
      println(">>(FAIL) : DMG{" + attackPower + "}, ARMOR{" + opponentArmor + "}")
    }
  }

  /**
    * moves the entity towards an opponent entity
    * @param entity
    */
  def moveInDirectionOf(entity: Entity) : Unit = {
    // computes the difference between them
    val distanceBetweenEntities: Float = helper.distanceBetween(this, entity)

    // threshold to round the coordinates
    val minDistanceToHit = 10

    // if about to collide, then we round the coordinates
    if (distanceBetweenEntities - this.speed < minDistanceToHit) {
      println(">>ABOUT TO COLLIDE: SAME COORDINATES : {" + this.getName() + "," + entity.getName() + "}")

      // we set the same coordinates
      this.coordX = entity.getX()
      this.coordY = entity.getY()

    } else {
      /*
       pythagorus theorem
      */

      // compute the difference on both axis
      val diffX: Float = entity.getX() - this.coordX
      val diffY: Float = entity.getY() - this.coordY

      // compute the distance we can go on both axis
      val moveX: Float = this.speed * (diffX / distanceBetweenEntities) // cosinus teta
      val moveY: Float = this.speed * (diffY / distanceBetweenEntities) // sinus teta

      // and move
      this.coordX += moveX
      this.coordY += moveY

      // debug
      println(">>" + this.getName() + " MOVING TOWARDS " + entity.getName() + " BY (" + moveX + "," + moveY + ") => (" + this.getX() + "," + this.getY() + ")")
      println(">>DISTANCE IS NOW : " + helper.distanceBetween(this, entity))
    }
  }

  /**
    * prints the summary of an iteration, debug purpose
     */
  def printSummary() : Unit = {
    println(this.getName() + ", PV = " + this.getHealth() + " @ (" + this.getX() + ", " + this.getY() + ")")
  }

  /**
    * returns a string representing the entity, debug purpose
    * @return
    */
  override def toString: String = {
    return name + ": (" + coordX + "," + coordY + ") : armor " + armor + ", regen " + regen + ", health " + health + ", speed " + speed
  }
}
