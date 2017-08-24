import universe._
import java.util.concurrent.TimeUnit
import vec.vec2
import scala.concurrent._
import scala.concurrent.duration.Duration
import java.net._
import java.io._
import java.util.Timer
import spray.json._
import DefaultJsonProtocol._
import akka.actor._

object Server {
	val step : Double = 0.1
	var U : Universe = _
	var exitFlag : Boolean = false

	class UniverseHandler extends java.util.TimerTask {
		def run() {
			U.tick(step)
			U.print()
		}
	}
	
	def main(args: Array[String]) {
		val system = ActorSystem("universe")
		
		val C = 1000000000.0
		U = new Universe(List(system.actorOf(Props(new StaticBody(100 * C, new vec2(0,0), new vec2(0,0), "Basketball")), name="Basketball"),
							  system.actorOf(Props(new StaticBody(C, new vec2(1,0), new vec2(0,scala.math.sqrt(0.00000000006673 * 100 * C)), "Tennis Ball")), name="TennisBall")))
		//U = new Universe(List(system.actorOf(Props(new StaticBody(1989000000000000000000000000000.0, new vec2(0, 0), new vec2(0, 0), "Sun")), name="Sun"),
		//					  system.actorOf(Props(new StaticBody(328500000000000000000000.0, new vec2(57910000000.0, 0), new vec2(0, 47400), "Mercury")), name="Mercury")))
		//					  system.actorOf(Props(new StaticBody(4867000000000000000000000.0, new vec2(108200000000.0, 0), new vec2(0, 0), "Venus")), name="Venus"),
		//					  system.actorOf(Props(new StaticBody(5972200000000000000000000.0, new vec2(149600000000.0, 0), new vec2(0, 0), "Earth")), name="Earth"),
		//					  system.actorOf(Props(new StaticBody(639000000000000000000000.0, new vec2(227900000000.0, 0), new vec2(0, 0), "Mars")), name="Mars"),
		//					  system.actorOf(Props(new Ship(10000.0, new vec2(149600040000.0,0), new vec2(0,100), "Hab")), name="Hab")))

		val t = new java.util.Timer()
		t.schedule(new UniverseHandler, (1000 * step).toLong, (1000 * step).toLong)
		//t.schedule(new UniverseHandler, (1000 * step).toLong, 5.toLong)

		scala.io.StdIn.readLine()
	
		exitFlag = true
		t.cancel()
	
		U.shutdown()		
		system.terminate
	}
}