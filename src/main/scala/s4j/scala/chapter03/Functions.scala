package s4j.scala.chapter03

/**
  * 02 - Defining functions
  *
  * Function and method definitions start with the def keyword, followed by the signature.
  * The signature looks similar to a Java method signature but with the parameter types the
  * other way around again, and the return type at the end rather than the start.
  */
object Functions {

  def main(args: Array[String]) {
    println(min(34, 3))
    println(min(10, 50))
    println(min2(300, 43))
    println(buggy_min(43, 300))
    println(min3(43, 300))
    println(min4(43, 300))
  }

  def min(x: Int, y: Int): Int = {
    if (x < y)
      return x
    else
      return y
  }

  val res1 = min(34, 3)          // res1: Int = 3
  val res2 = min(10, 50)         // res2: Int = 10


  // return can be dropped
  def min2(x: Int, y: Int): Int = {
    if (x < y)
      x
    else
      y
  }

  val res3 = min(300, 43)         // res3: Int = 43


  // last statement as a return value
  def buggy_min(x: Int, y: Int): Int = {
    if (x < y)
      x
    y         // bug! where's the else?
  }

  val res4 = min(10, 230)         // res4: Int = 230


  // the return type can be omitted
  // If you don’t use any return statements, the return type can usually be inferred.
  def min3(x: Int, y: Int) = {
    if (x < y)
      x
    else
      y
  }


  def min4(x: Int, y: Int) = if (x < y) x else y


  // doesn't compile (missing = symbol)

  //  def min5(x: Int, y: Int) {
  //    if (x < y) x else y
  //  }

}
