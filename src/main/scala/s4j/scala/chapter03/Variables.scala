package s4j.scala.chapter03

/**
  * 01 - Defining Values and Variables
  */
object Variables {

  def main(args: Array[String]) {
    val immutable: String = "Scala"     // immutable can not be reassigned
    println(immutable);

    var language: String = "Java"
    language = "Scala"                  // var can be reassigned
    println(language);

    val age = 35
    var maxHeartRate = 210 - age * .5   // maxHeartRate: Double = 191.5
    println(maxHeartRate);
  }
}
