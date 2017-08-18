package universe

import vec.vec2;

trait SpatialObject {
  var pos : vec2 = new vec2(0, 0);
  var vel : vec2 = new vec2(0, 0);
  def dist(o : SpatialObject) : Double = {
    return pos dist o.pos
  }
}