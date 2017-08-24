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

class Ship(name : String = "") extends Body(name) {
	var dir : Double = 0
	var component = List[ActorRef](context.actorOf(Props(new universe.component.engine.BasicEngine(this)), name="Engine1"))

	def this(_mass : Double, _pos : vec2, _vel : vec2,  _name : String) {
    	this(_name)
    	mass = _mass
    	pos = _pos
    	vel = _vel
  	}

  	def getForces(obj : PhysicalObject) : List[vec2] = {
  		List(G(obj))
  	}


  	import context.dispatcher
  	override def applyPhysics(U : Universe, step : Double) {
  		val l = component.map(x => (x ? GetForces(this)))
  		val fl = Future.sequence(l)
  		fl.onComplete {
  			case Success(res : List[vec.vec2]) => 
				val F = res.foldLeft(new vec.vec2(0.0,0.0))((x, y) => x + y)
				val acc = F / mass
				vel += acc * step
			case Failure(t) =>
				println("An issue has occurred in calculating forces on " + name + ". Send this to #programming, maybe they can help:\n")
				t.printStackTrace
			case _ =>
				println("An unknown error has occurred in calculating forces on " + name + ".")	
  		}
  		super.applyPhysics(U, step)
  	}
}