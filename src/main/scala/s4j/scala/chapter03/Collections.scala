package s4j.scala.chapter03

/**
  * 04 - Collections
  *
  * Scala comes with immutable collection types (like Set, List, and Map) as well as mutable
  * versions with the same API. The creators of Scala (and the functional programming
  * community in general) favor immutability, so when you create a collection in Scala, the
  * immutable version is the default choice.
  */
object Collections {

  def main(args: Array[String]) {
    val list = List("a", "b", "c") // we can create a list with the following

    val map = Map(1 -> "a", 2 -> "b")

    list.foreach(value => println(value))

    // short hand version (similar to method reference in Java)
    list.foreach(println)

    // different ways to process a list with a 'for' loop

    for (value <- list) println(value)

    for (value <- list.reverse) println(value)

    for (value <- list) {
      println(value)
    }

    (0 to 100).foreach(println(_))
  }
}
