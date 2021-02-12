package com.allaboutscala.chap09

import scala.util.{Failure, Success}

object Tutorial_13_Future_Zip extends App {

  println("Step 1: Define a method which returns a Future Option")
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future
  def donutStock(donut: String): Future[Option[Int]] = Future {
    println("checking donut stock")
    if(donut == "vanilla donut") Some(10) else None
  }

  println(s"\nStep 2: Define a method which returns a Future Double for donut price")
  def donutPrice(): Future[Double] = Future.successful(3.25)

  println(s"\nStep 3: Zip the values of the first future with the second future")
  val donutStockAndPriceOperation = donutStock("vanilla donut") zip donutPrice()
  donutStockAndPriceOperation.onComplete {
    case Success(results) => println(s"Results $results")
    case Failure(e)       => println(s"Error processing future operations, error = ${e.getMessage}")
  }

  Thread.sleep(3000)
}
