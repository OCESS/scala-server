package universe

import vec.vec2

class Planet(name : String = "") extends Body(name) {
  def this(_mass : Double, _pos : vec2, _vel : vec2,  _name : String) {
    this(_name)
    mass = _mass
    pos = _pos
    vel = _vel
  }
  override def tick(obj : List[PhysicalObject], time : Double) {
    super.tick(obj, time)
  }
}