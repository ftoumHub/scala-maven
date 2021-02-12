package com.allaboutscala.chap09

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Tutorial_06_Future_Option_Map extends App {

  println("Step 1: Define a method which returns a Future Option")
  def donutStock(donut: String): Future[Option[Int]] = Future {
    // assume some long running database operation
    println("checking donut stock")
    if(donut == "vanilla donut") Some(10) else None
  }

  println(s"\nStep 2: Access value returned by future using map() method")
  donutStock("vanilla donut")
    .map(someQty => println(s"Buying ${someQty.getOrElse(0)} vanilla donuts"))

  Thread.sleep(3000)
}
