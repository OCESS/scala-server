package universe

abstract class Body(val name : String = "") extends PhysicalObject {
  override def tick(obj : List[PhysicalObject], time : Double) {
    super.tick(obj, time)
  }
}