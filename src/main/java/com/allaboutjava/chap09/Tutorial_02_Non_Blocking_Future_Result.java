package com.allaboutjava.chap09;

import io.vavr.concurrent.Future;
import io.vavr.control.Try;

import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;
import static io.vavr.Predicates.instanceOf;

public class Tutorial_02_Non_Blocking_Future_Result {

    public static void main(String[] args) throws InterruptedException {

        println("Step 1: Define a method which returns a Future");
        Function<String, Future<Integer>> donutStock = __ -> Future.of(() -> {
            // assume some long running database operation
            Thread.sleep(500);
            println("checking donut stock");
            return 10;
        });

        println("\nStep 2: Call method which returns a Future");
        /**donutStock.apply("vanilla donut")
         .onSuccess(stock -> printf("Stock for vanilla donut = %s", stock))
         .onFailure(e -> printf("Failed to find vanilla donut stock, exception = %e"))
         .get();*/
        donutStock.apply("vanilla donut")
                .onComplete(response ->
                        Match(response).of(
                                Case($Success($(instanceOf(Integer.class))), stock -> run(() -> printf("Stock for vanilla donut = %s", stock))),
                                Case($Failure($()), e -> run(e::printStackTrace))
                        ));
        Thread.sleep(3000);
    }
}
