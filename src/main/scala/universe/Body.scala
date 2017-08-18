package universe

import akka.actor._

abstract class Body(val name : String = "") extends PhysicalObject {
	override def G(o : PhysicalObject) {
		return super.G(o)
	}
}