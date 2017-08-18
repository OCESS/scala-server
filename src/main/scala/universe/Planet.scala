package universe

import vec.vec2

class Planet(name : String = "") extends Body(name) with Actor {
  def this(_mass : Double, _pos : vec2, _vel : vec2,  _name : String) {
    this(_name)
    mass = _mass
    pos = _pos
    vel = _vel
  }

  implicit val timeout = Timeout(5 millis)
	val f = Future.sequence(obj.map(x => (x ? Grav(this))))
	f.onComplete {
			case Success(res) => {
				res.foldRight(new vec2(0,0))((a, b) => b + (a match {
					case v : vec2 => v
					case _ => println("Something went horribly wrong"); new vec2(0,0)}))
				
			}
			case Failure(t) => println("An error has occured: " + t.getMessage); new vec2(0, 0)
		}

	def receive {
		case Grav(o) => 
	}
}