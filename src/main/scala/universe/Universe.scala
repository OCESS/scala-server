package universe

import akka._
import akka.actor._

class Universe(val actor : List[ActorRef] = List()) {
	def tick(step : Double) {
		actor.map(x => x ! TickMessage(this, step))
	}
	def ++(a : ActorRef) : Universe = {
		return new Universe(a :: actor)
	}
	def print() {
		actor.map(x => x ! PrintLoc())
	}
	def shutdown() {
		actor.map{x => x ! PoisonPill}
	}
}