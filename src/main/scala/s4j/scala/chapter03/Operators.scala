package s4j.scala.chapter03

import scala.language.postfixOps
/**
  * 03 - Operator Overloading and Infix Notation
  */
object Operators {

  def main(args: Array[String]) {
    val age: Int = 34

    println(age.*(.5))                     // The Int age calls the * method
    println(5.*(10))                       // 5 is an Int, Int has the * method
    println(age * .5)                      // infix notation

    println(35 toString)                   // toString is also a method on Int

    println(35 + 10)                       // + is a method too (infix example)

    println("aBCDEFG" replace("a", "A"))   // as is replace (infix example)

    val train = "6:30 from London"
    val passenger = "Mr Smith"

    println(train + passenger)
  }
}
