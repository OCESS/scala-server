package universe

import vec.vec2
import akka._
import akka.actor._

// Request for the net force exerted by this object with optional direction
case class NetForce(dir : Double = 0.0)

// Request for the gravitational force between the pair (obj, this)
case class Grav(obj : PhysicalObject)

// Request to tick 'step' units of time into the future by communicating with actors
case class TickMessage(actor : List[ActorRef], step : Double)

// Response containing location of a body
case class LocInfo(pos : vec2, vel : vec2, name : String)

// Request to respond with location
case class GetLoc()

// Request to print location
case class PrintLoc()