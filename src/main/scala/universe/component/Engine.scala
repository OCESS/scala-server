package universe.component

import vec.vec2
import universe._

abstract class Engine(owner : Ship, val thrust : Double, var pct : Double = 0)  extends universe.Component(owner) {
	override def receive = {
		case GetForces(s : Ship) => sender() ! (if (s eq owner) (new vec2(pct * thrust, 0)).rotate(owner.dir) else new vec2(0,0))
		case _ => println("Engine doesn't know what to do with this")
	}
}