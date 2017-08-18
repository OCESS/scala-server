package universe

trait TemporalObject {
  def tick(obj : List[PhysicalObject], time : Double);  
}