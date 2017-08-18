package universe

import vec.vec2
import akka.actor._

trait Component extends Actor {
  def netForce() : vec2 = {
    return new vec2(0,0)
  }
}