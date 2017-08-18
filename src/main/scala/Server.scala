import universe._
import java.util.concurrent.TimeUnit
import vec.vec2
import scala.concurrent._
import java.net._
import java.io._
import java.util.Timer
import spray.json._
import DefaultJsonProtocol._
import akka._
import akka.actor._

object Server {
	val step : Double = 0.05
	val port : Int = 30000
	val serverSock : ServerSocket = new ServerSocket(port);
	
	var U : Universe = _;
	var exitFlag : Boolean = false

	def readSocket(socket: Socket): String = {
		val bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream))
		var request = ""
		var line = ""
		do {
			line = bufferedReader.readLine()
			if (line == null) {
				println("Stream terminated")
				return request
			}
			request += line + "\n"
		} while (line != "")
		request
	}
	
	class ServerListener(val port : Int) extends Runnable {
		var serverSock : ServerSocket = _
		def run() {
			serverSock = new ServerSocket(port)
			while (!exitFlag) {
				val sock = serverSock.accept()
				println(readSocket(sock))
				sock.close()
			}
			serverSock.close()
		}
	}
	
	class UniverseHandler extends java.util.TimerTask {
		def run() {
			U.tick(step)
			U.print()
		}
	}
	
	def main(args: Array[String]) {
		val listener = new Thread(new ServerListener(port))
		listener.start()

		val system = ActorSystem("universe")

		U = new Universe(List(system.actorOf(Props(new Planet(1989000000000000000000000000000.0, new vec2(0, 0), new vec2(0, 0), "Sun")), name="Sun"),
							  system.actorOf(Props(new Planet(328500000000000000000000.0, new vec2(57910000000.0, 0), new vec2(0, 47400), "Mercury")), name="Mercury")))
		/*					  system.actorOf(Props(new Planet(4867000000000000000000000.0, new vec2(108200000000.0, 0), new vec2(0, 0), "Venus")), name="Venus"),
							  system.actorOf(Props(new Planet(5972200000000000000000000.0, new vec2(149600000000.0, 0), new vec2(0, 0), "Earth")), name="Earth"),
							  system.actorOf(Props(new Planet(639000000000000000000000.0, new vec2(227900000000.0, 0), new vec2(0, 0), "Mars")), name="Mars"),
							  system.actorOf(Props(new Ship(10000.0, new vec2(149600040000.0,0), new vec2(1,0), "Hab")), name="Hab")))*/
		
		val t = new java.util.Timer()
		t.schedule(new UniverseHandler, (1000 * step).toLong, (1000 * step).toLong)
		
		scala.io.StdIn.readLine()
		exitFlag = true
	
		t.cancel()
		listener.join()
	}
}