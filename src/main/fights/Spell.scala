package fights

class Spell(val name: String, val range: Int, val min: Int, val max: Int) extends Serializable {

  def getRange() : Int = {
    return this.range
  }

  def getDamages(): Int = {
    return new MathHelper().getRandom(this.min, this.max)
  }

  override def toString: String = {
    return name + ": " + min + "=>" + max + " @ " + range
  }
}