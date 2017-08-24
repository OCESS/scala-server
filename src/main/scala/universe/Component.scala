package universe

import vec.vec2
import akka.actor._

abstract class Component(val owner : Ship) extends Actor {
  def receive = {
  	case GetForces(x) => new vec2(0,0)
  	case _ => println("Component doesn't know what to do with this")
  }
}