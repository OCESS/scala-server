package universe.component

import vec.vec2

abstract class Engine(val thrust : Double, var pct : Double = 0)  extends universe.Component {
  override def netForce() : vec2 = {
    return new vec2(pct * thrust, 0)
  }
}