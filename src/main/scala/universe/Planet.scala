package universe

import vec.vec2
import akka._
import akka.actor._
import scala.concurrent.Await
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.{Success, Failure}

class Planet(name : String = "") extends Body(name) with Actor {
	import context.dispatcher
	def this(_mass : Double, _pos : vec2, _vel : vec2,  _name : String) {
    	this(_name)
    	mass = _mass
    	pos = _pos
    	vel = _vel
  	}

	def tick(actor : List[ActorRef], step : Double) {
  		implicit val timeout = Timeout(500 millis)
		val f = Future.sequence(actor.map(x => (x ? Grav(this))))
		f.onComplete {
			case Success(res) => {
				val F = res.foldRight(new vec2(0,0))((a, b) => b + (a match {
					case v : vec2 => v
					case _ => println("Something went horribly wrong"); new vec2(0,0)}))
				val a = F / mass
				pos += vel * step
				vel += a * step
			}
			case Failure(t) => println("An error has occured: " + t.getMessage)
		}		
	}

	def receive = {
		case Grav(o) => sender() ! super.G(o)
		case TickMessage(actor, step) => tick(actor, step)
		case GetLoc() => sender() ! LocInfo(pos, vel, name)
		case PrintLoc() => println(f"${name}: ${pos.x}%.0f ${pos.y}%.0f")
	}
}