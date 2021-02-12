package com.allaboutscala.chap09

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

/**
 * Chain futures using flatMap
 */
object Tutorial_03_Chain_Futures_FlatMap extends App {

  println("Step 1: Define a method which returns a Future")
  def donutStock(donut: String): Future[Int] = Future {
    // assume some long running database operation
    println("checking donut stock")
    10
  }

  println("\nStep 2: Define another method which returns a Future")
  def buyDonuts(quantity: Int): Future[Boolean] = Future {
    println(s"buying $quantity donuts")
    true
  }

  println("\nStep 3: Chaining Futures using flatMap")
  // To sequence multiple futures in order, you can make use of the flatMap() method. In our
  // example, we will chain the two future operations donutStock() and buyDonuts() by using
  // flatMap() method as shown below.
  val buyingDonuts: Future[Boolean] = donutStock("plain donut")
                                            .flatMap(qty => buyDonuts(qty))
  val isSuccess = Await.result(buyingDonuts, 5 seconds)
  println(s"Buying vanilla donut was successful = $isSuccess")
}
