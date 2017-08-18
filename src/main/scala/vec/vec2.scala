package vec

import scala.math.pow;
import scala.math.abs;
import scala.math.sqrt;
import scala.math.atan2;
import scala.math.cos;
import scala.math.sin;

class vec2(val x : Double, val y : Double) {
  def dist(other : vec2) : Double = {
    return sqrt(pow(other.x - x, 2.0) + pow(other.y - y, 2.0))
  }
  def mag() : Double = {
    return sqrt(x*x+y*y);
  }
  def +(other : vec2) : vec2 = {
    return new vec2(x + other.x, y + other.y)
  }
  def -(other : vec2) : vec2 = {
    return new vec2(x - other.x, y - other.y)
  }
  def /(other : Double) : vec2 = {
    return new vec2(x / other, y / other)
  }
  def *(other : Double) : vec2 = {
    return new vec2(x * other, y * other)
  }
  def unit(other : vec2) : vec2 = {
    val theta = atan2((other - this).y, (other - this).x)
    return new vec2(cos(theta), sin(theta))
  }
  def rotate(theta : Double) : vec2 = {
    return new vec2(x * cos(theta) - y * sin(theta), x * sin(theta) + y * cos(theta))
  }
}