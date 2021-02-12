package com.allaboutscala.chap09


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * Non blocking future result
 *
 * - With <b>Future.onComplete()</b> we are no longer blocking for the result from the Future but
 *   instead we will receive a callback for either a Success or a Failure.
 * - As such, we've also had to import scala.util.{Failure, Success}
 * - Surely though the Thread.sleep() is blocking our main thread so that we can see the
 *   asynchronous result from the future. In a real application, you will most certainly not use
 *   Thread.sleep() but instead "react" to the result returned by the future.
 */
object Tutorial_02_Non_Blocking_Future_Result extends App {

  println("Step 1: Define a function which returns a Future")
  def donutStock(donut: String): Future[Int] = Future {
    Thread.sleep(500) // assume some long running database operation
    println("checking donut stock")
    10
  }

  println("\nStep 2: Non blocking future result")
  // Instead of blocking our main program using Await.result(), we will make use of
  // Future.onComplete() callback to capture the result of a Future.
  donutStock("vanilla donut").onComplete {
    case Success(stock) => println(s"Stock for vanilla donut = $stock")
    case Failure(e) => println(s"Failed to find vanilla donut stock, exception = $e")
  }
  Thread.sleep(3000) // On laisse le temps à la future de compléter
}
