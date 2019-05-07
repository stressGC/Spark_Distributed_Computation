/**
  * @author Georges Cosson
  */
package fights

/**
  * class used to represent the spell of an entity
  * @param name name of the spell
  * @param range range of the spell
  * @param min min damage dealt by the spell
  * @param max max damage fealt by the spell
  */
class Spell(val name: String, val range: Int, val min: Int, val max: Int) extends Serializable {

  /* accessors */
  def getRange() : Int = {
    return this.range
  }

  def getName() : String = {
    return this.name
  }

  def getDamages(): Int = {
    return new MathHelper().getRandom(this.min, this.max)
  }

  /* methods */
  override def toString: String = {
    return name + ": " + min + "=>" + max + " @ " + range
  }
}