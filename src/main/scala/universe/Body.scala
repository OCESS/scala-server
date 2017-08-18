package universe

import akka.actor._

abstract class Body(val name : String = "") extends PhysicalObject {
	override def tick(obj : List[PhysicalObject], time : Double) {
    	super.tick(obj, time)
  	}
  	override def netForce(obj : List[ActorRef]) {
  		super.netForce(obj)
  	}
}