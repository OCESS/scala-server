package universe

import vec.vec2
import akka._
import akka.actor._

case class Grav(obj : PhysicalObject)
case class TickMessage(actor : List[ActorRef], step : Double)
case class LocInfo(pos : vec2, vel : vec2, name : String)
case class GetLoc()
case class PrintLoc()