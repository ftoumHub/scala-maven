package com.allaboutscala.chap09

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * Method with future as return type.
 */
object Tutorial_01_Function_With_Future_Return_Type extends App {

  println("Step 1: Define a function which returns a Future")
  def donutStock(donut: String): Future[Int] = Future {
    // assume some long running database operation
    println("checking donut stock")
    10
  }

  println("\nStep 2: Call method which returns a Future")
  val vanillaDonutStock = Await.result(donutStock("vanilla donut"), 5 seconds)
  println(s"Stock of vanilla donut = $vanillaDonutStock")
}
