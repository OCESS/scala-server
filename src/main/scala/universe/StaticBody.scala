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

class StaticBody(name : String = "") extends Body(name) {
	def this(_mass : Double, _pos : vec2, _vel : vec2,  _name : String) {
    	this(_name)
    	mass = _mass
    	pos = _pos
    	vel = _vel
  	}

  	def getForces(o : PhysicalObject) : List[vec2] = {
  		List(G(o))
  	}
}