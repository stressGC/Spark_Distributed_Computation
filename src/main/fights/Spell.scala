package fights

class Spell(val name: String, val range: Int, val min: Int, val max: Int) {
  def getDamages(): Int = {
    println("rnd dmg between " + min + " & " + max)
    return new Helper().getRandom(this.min, this.max)
  }

  override def toString: String = {
    return name + ": " + min + "=>" + max + " @ " + range
  }
}