package fights

import scala.math.abs

class Entity(val name: String, val armor: Int, val regen: Int, var health: Int, var coordX: Float, var coordY: Float, val speed: Int, val spell: Spell) {
  private var isDead = false
  private val helper = new Helper()

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
    if(this.health + difference <= 0) {
      this.isDead = true
    } else this.health += difference
  }

  def getClosestEntity(entity1: Entity, entity2: Entity) : Entity = {
    return entity1
  }

  def attack(opponent: Entity) : Unit = {
    // lets get the damage of our spell
    val attackPower = this.spell.getDamages();

    // lets get opponent armor
    val opponentArmor = opponent.getArmor();

    // if we deal more than opponent has armor
    if (attackPower > opponentArmor) {
      println("(OK) spell : " + attackPower + " armor : " + opponentArmor)
      this.modifyHealth(attackPower)
    } else {
      println("(FAIL) spell : " + attackPower + " armor : " + opponentArmor)
    }
  }

  def moveInDirectionOf(entity: Entity) : Unit = {
    val distanceBetweenEntities: Float = helper.distanceBetween(this, entity)

    val minDistanceToHit = 10

    // if about to collide, then we round the coordinates
    if (distanceBetweenEntities - this.speed < minDistanceToHit) {
      this.coordX = entity.getX()
      this.coordY = entity.getY()

    } else {
      // pythagorus theorem
      val diffX: Float = abs(entity.getX() - this.coordX)
      val diffY: Float = abs(entity.getY() - this.coordY)

      val moveX: Float = this.speed * (diffX / distanceBetweenEntities) // cosinus teta
      val moveY: Float = this.speed * (diffY / distanceBetweenEntities) // sinus teta

      this.coordX += moveX
      this.coordY += moveY
    }
  }

  override def toString: String = {
    return name + ": (" + coordX + "," + coordY + ") : armor " + armor + ", regen " + regen + ", health " + health + ", speed " + speed
  }
}
