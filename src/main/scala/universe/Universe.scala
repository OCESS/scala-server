package universe

class Universe(val body : List[Body] = List()) {
  def tick(step : Double) {
    body.map(x => x.tick(body, step))
  }
  def ++(b : Body) : Universe = {
    return new Universe(b :: body)
  }
  def print() {
    body.map(x => println(f"${x.name}: ${x.netForce(body).x} ${x.netForce(body).y} ${x.pos.x}%.0f ${x.pos.y}%.0f"))
  }
}