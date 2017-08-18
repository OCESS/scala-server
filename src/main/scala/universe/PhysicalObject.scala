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

trait PhysicalObject extends SpatialObject {
	var mass : Double = 0.0

	def G(b : PhysicalObject) : vec2 = {
		if ((this dist b) <= scala.math.pow(10, -6))
			return new vec2(0.0, 0.0)             
		else
			return pos.unit(b.pos) * 0.00000000006673 * b.mass * mass / scala.math.pow((this dist b), 2.0)
	}
}