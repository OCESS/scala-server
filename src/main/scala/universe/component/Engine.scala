package universe.component

import vec.vec2
import universe._

abstract class Engine(val thrust : Double, var pct : Double = 0)  extends universe.Component {
	def receive = {
		case NetForce(dir) => sender() ! (new vec2(pct * thrust, 0)).rotate(dir)
	}
}