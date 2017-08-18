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

trait PhysicalObject extends SpatialObject with Actor {
	import context.dispatcher
	var mass : Double = 0.0

	def G(b : PhysicalObject) : vec2 = {
		if ((this dist b) <= scala.math.pow(10, -6))
			return new vec2(0.0, 0.0)             
		else
			return (pos - b.pos) * 0.00000000006673 * b.mass * mass / scala.math.pow((this dist b), 2.0)
	}

	def NetGrav(obj : List[ActorRef]) {
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
	}

	def tick(obj : List[ActorRef], time : Double) {
		val a = F / mass
		pos += vel * time
		vel += a * time
	}

	def receive = {
		case NetForce(actor) => sender ! NetGrav(actor)
		case Grav(o) => sender ! G(o)
		case TickMessage(actor, step) => tick(actor, step)
		case PrintLoc() => println(f"${pos.x}%.0f ${pos.y}%.0f")
		case _ => println("Unrecognized message")
	}
}