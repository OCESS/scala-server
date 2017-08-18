package universe

import vec.vec2
import akka.actor._

abstract class Body(val name : String = "") extends PhysicalObject {
	override def G(o : PhysicalObject) : vec2 = {
		return super.G(o)
	}
}