package universe

import vec.vec2

trait Component {
  def netForce() : vec2 = {
    return new vec2(0,0)
  }
}