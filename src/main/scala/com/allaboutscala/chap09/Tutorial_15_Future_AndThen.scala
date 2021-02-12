package com.allaboutscala.chap09

object Tutorial_15_Future_AndThen extends App {

  println("Step 1: Define a method which returns a Future")
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future
  def donutStock(donut: String): Future[Int] = Future {
    // assume some long running database operation
    println("checking donut stock")
    10
  }

  println(s"\nStep 2: Call Future.andThen with a PartialFunction")
  val donutStockOperation = donutStock("vanilla donut")
  donutStockOperation.andThen { case stockQty => println(s"Donut stock qty = $stockQty")}

  Thread.sleep(3000)
}
