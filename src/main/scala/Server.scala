import universe._
import java.util.concurrent.TimeUnit
import vec.vec2
import scala.concurrent._
import java.net._
import java.io._
import java.util.Timer
import spray.json._
import DefaultJsonProtocol._ // if you don't supply your own Protocol (see below)

object Server {
  val step : Double = 1000.0
  val port : Int = 8082
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
    val source = """{ "some": "JSON source" }"""
    val jsonAst = source.parseJson // or JsonParser(source)
    println(jsonAst.prettyPrint)

    val listener = new Thread(new ServerListener(port))
    listener.start()
    
    /*U = new Universe(List(new Planet(1989000000000000000000000000000.0, new vec2(0, 0), new vec2(0, 0), "Sun"),
                              new Planet(328500000000000000000000.0, new vec2(57910000000.0, 0), new vec2(0, 0), "Mercury"),
                              new Planet(4867000000000000000000000.0, new vec2(108200000000.0, 0), new vec2(0, 0), "Venus"),
                              new Planet(5972200000000000000000000.0, new vec2(149600000000.0, 0), new vec2(0, 0), "Earth"),
                              new Planet(639000000000000000000000.0, new vec2(227900000000.0, 0), new vec2(0, 0), "Mars")))*/
    U = new Universe(List(new Ship("Hab", 10000)))
    val x : Ship = U.body(0) match {
      case x2 : Ship => x2
      case _ => throw new Exception
    }
    val e : universe.component.Engine = x.component(0) match {
      case e2 : universe.component.Engine => e2
      case _ => throw new Exception
    }
    
    /* Fire engines at 50%, angled 30o from diagonal */
    e.pct = 0.5
    x.dir = scala.math.Pi / 6
    
    val t = new java.util.Timer()
    t.schedule(new UniverseHandler, step.toLong, step.toLong)
    
    scala.io.StdIn.readLine()
    exitFlag = true
    
    t.cancel()
    listener.join()
  }
}