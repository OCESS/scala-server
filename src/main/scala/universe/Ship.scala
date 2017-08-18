package universe

import universe.component.engine._
import vec.vec2

class Ship(name : String = "") extends Body(name) {
  var dir : Double = 0
  var component = List[Component](new BasicEngine())
  
  def this(name : String, mass : Double) {
    this(name)
    this.mass = mass
  }
  override def netForce(obj : List[PhysicalObject]) : vec2 = {
    return super.netForce(obj) + (component.foldRight(new vec2(0,0))((b, a) => a + b.netForce().rotate(dir)))
  }
}