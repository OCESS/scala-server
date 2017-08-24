package universe

import vec.vec2
import akka.actor._
import scala.concurrent.Await
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.{Success, Failure}

abstract class Body(val name : String = "") extends PhysicalObject with Actor {
	import context.dispatcher
	implicit val timeout = Timeout(500 millis)

	// Returns a list of all forces acting between this object and children and o
	def getForces(o : PhysicalObject) : List[vec2];

	// Ticks this object 'step' units of time to the future
	def tick(U : Universe, step : Double) {
		// Update position
		pos += vel * step

		// Apply external forces to the universe
		applyPhysics(U, step)
	}

	// Applies physics between this object and everything in the universe
	def applyPhysics(U : Universe, step : Double) {	
		val ctx = this

		U.actor.map(a => {
			val f = a ? GetForces(ctx)
			f.onComplete {
				case Success(res : List[vec.vec2]) => 
					val F = res.foldLeft(new vec.vec2(0.0,0.0))((x, y) => x + y)
					val acc = F / mass
					vel += acc * step
				case Failure(t) =>
					println("An issue has occurred in calculating forces on " + name + ". Send this to #programming, maybe they can help:\n")
					t.printStackTrace
				case _ =>
					println("An unknown error has occurred in calculating forces on " + name + ".")
			}
			f
		})
	}

	def receive = {
		case GetForces(o) => sender() ! getForces(o)
		case TickMessage(u, step) => tick(u, step)
		case GetLoc() => sender() ! LocInfo(pos, vel, name)
		case PrintLoc() => println(f"${pos.x}%.2f,${pos.y}%.2f")
	}
}