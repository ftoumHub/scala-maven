package com.allaboutscala.chap09

import scala.util.{Failure, Success}

object Tutorial_17_Future_Recover extends App {

  println("Step 1: Define a method which returns a Future")
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future
  def donutStock(donut: String): Future[Int] = Future {
    if(donut == "vanilla donut") 10
    else throw new IllegalStateException("Out of stock")
  }

  println("\nStep 2: Execute donutStock() future operation")
  donutStock("vanilla donut")
    .onComplete {
      case Success(donutStock)  => println(s"Results $donutStock")
      case Failure(e)           => println(s"Error processing future operations, error = ${e.getMessage}")
    }

  println("\nStep 3: Call Future.recover to recover from a known exception")
  donutStock("unknown donut")
    .recover { case e: IllegalStateException if e.getMessage == "Out of stock" => 0 }
    .onComplete {
      case Success(donutStock)  => println(s"Results $donutStock")
      case Failure(e)           => println(s"Error processing future operations, error = ${e.getMessage}")
    }

  Thread.sleep(3000)
}
