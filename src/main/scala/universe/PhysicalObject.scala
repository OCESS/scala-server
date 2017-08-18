package universe

import vec.vec2;

trait PhysicalObject extends SpatialObject with TemporalObject {
  var mass : Double = 0.0

  def netForce(obj : List[PhysicalObject]) : vec2 = {
    val l : List[vec2] = (obj.map(b =>
      if ((this dist b) <= scala.math.pow(10, -6))
          new vec2(0.0, 0.0)             
      else
          (pos unit b.pos) * 0.00000000006673 * b.mass * mass / scala.math.pow((this dist b), 2.0)))
    def sum(ls : List[vec2]) : vec2 = {
      ls match {
        case Nil => new vec2(0.0, 0.0)
        case v :: rst => v + sum(rst) 
      }
    }
    return l.foldLeft(new vec2(0, 0))((b, a) => a + b) 
  }
  
  override def tick(obj : List[PhysicalObject], time : Double) {
    val F = netForce(obj)
    val a = F / mass
    pos += vel * time
    vel += a * time 
  }
}