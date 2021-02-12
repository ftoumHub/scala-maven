package com.allaboutscala.chap09

import scala.util.{Failure, Success}

object Tutorial_19_Future_FallbackTo extends App {

  println("Step 1: Define a method which returns a Future")
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future
  def donutStock(donut: String): Future[Int] = Future {
    if(donut == "vanilla donut") 10
    else throw new IllegalStateException("Out of stock")
  }



  println("\nStep 2: Define another method which returns a Future to match a similar donut stock")
  def similarDonutStock(donut: String): Future[Int] = Future {
    println(s"replacing donut stock from a similar donut = $donut")
    if(donut == "vanilla donut") 20 else 5
  }



  println("\nStep 3: Call Future.fallbackTo")
  val donutStockOperation = donutStock("plain donut")
    .fallbackTo(similarDonutStock("vanilla donut"))
    .onComplete {
      case Success(donutStock)  => println(s"Results $donutStock")
      case Failure(e)           => println(s"Error processing future operations, error = ${e.getMessage}")
    }

  Thread.sleep(3000)
}
