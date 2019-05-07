package fights

class Entity(val name: String, val armor: Int, val regen: Int, var health: Int, var coordX: Float, var coordY: Float, val speed: Int, val spell: Spell) extends Serializable {
  private val helper = new MathHelper()
  private val maxHealth = this.health

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

  def modifyHealth(difference: Int) : Unit = {
    if(difference < 0) {
      println(">>" + this.name + " LOST {" + difference + "} HP, FROM {" + this.health + "} TO {" + (this.health + difference) + "}")
    }
    if(this.health + difference <= 0) {
      println(">>" + this.name + " IS DEAD")
    }
    this.health += difference
  }

  def applyRegen() : Unit = {
    var msg = ""
    if(this.getHealth() + this.regen > this.maxHealth) {
      msg = (this.maxHealth - this.getHealth()).toString
    } else {
      this.modifyHealth(this.regen)
      msg = this.regen.toString
    }
    println(">>REGEN {+" + msg + "}, NOW {" + this.health + "}")
  }

  def attack(opponent: Entity) : Unit = {
    // lets get the damage of our spell
    val attackPower = this.spell.getDamages();

    // lets get opponent armor
    val opponentArmor = opponent.getArmor();

    // if we deal more than opponent has armor
    if (attackPower > opponentArmor) {
      println(">>(OK) : DMG{" + attackPower + "}, ARMOR{" + opponentArmor + "}")
      this.modifyHealth(-attackPower)
    } else {
      println(">>(FAIL) : DMG{" + attackPower + "}, ARMOR{" + opponentArmor + "}")
    }
  }

  def moveInDirectionOf(entity: Entity) : Unit = {
    val distanceBetweenEntities: Float = helper.distanceBetween(this, entity)

    val minDistanceToHit = 10

    // if about to collide, then we round the coordinates
    if (distanceBetweenEntities - this.speed < minDistanceToHit) {
      println(">>ABOUT TO COLLIDE: SAME COORDINATES : {" + this.getName() + "," + entity.getName() + "}")
      this.coordX = entity.getX()
      this.coordY = entity.getY()

    } else {
      // pythagorus theorem
      val diffX: Float = entity.getX() - this.coordX
      val diffY: Float = entity.getY() - this.coordY

      val moveX: Float = this.speed * (diffX / distanceBetweenEntities) // cosinus teta
      val moveY: Float = this.speed * (diffY / distanceBetweenEntities) // sinus teta
      this.coordX += moveX
      this.coordY += moveY

      println(">>" + this.getName() + " MOVING TOWARDS " + entity.getName() + " BY (" + moveX + "," + moveY + ") => (" + this.getX() + "," + this.getY() + ")")

      println(">>DISTANCE IS NOW : " + helper.distanceBetween(this, entity))
    }
  }

  def printSummary() : Unit = {
    println(this.getName() + ", PV = " + this.getHealth() + " @ (" + this.getX() + ", " + this.getY() + ")")
  }

  override def toString: String = {
    return name + ": (" + coordX + "," + coordY + ") : armor " + armor + ", regen " + regen + ", health " + health + ", speed " + speed
  }
}
