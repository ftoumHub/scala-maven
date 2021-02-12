package com.allaboutscala.chap09

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Tutorial_07_Composing_Futures extends App {

  println("Step 1: Define a method which returns a Future Option")
  def donutStock(donut: String): Future[Option[Int]] = Future {
    // assume some long running database operation
    println("checking donut stock")
    if (donut == "vanilla donut") Some(10) else None
  }

  println("\nStep 2: Define another method which returns a Future")
  def buyDonuts(quantity: Int): Future[Boolean] = Future {
    println(s"buying $quantity donuts")
    if (quantity > 0) true else false
  }

  println(s"\nStep 3: Calling map() method over multiple futures")
  val resultFromMap: Future[Future[Boolean]] = donutStock("vanilla donut")
    .map(someQty => buyDonuts(someQty.getOrElse(0)))
  Thread.sleep(1000)

  println(s"\nStep 4: Calling flatMap() method over multiple futures")
  val resultFromFlatMap: Future[Boolean] = donutStock("vanilla donut")
    .flatMap(someQty => buyDonuts(someQty.getOrElse(0)))
  Thread.sleep(1000)
}
