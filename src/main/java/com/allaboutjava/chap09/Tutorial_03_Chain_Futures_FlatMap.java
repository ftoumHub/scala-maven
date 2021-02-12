package com.allaboutjava.chap09;

import io.vavr.concurrent.Future;

import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;
import static io.vavr.Predicates.instanceOf;
import static java.lang.Boolean.TRUE;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Tutorial_03_Chain_Futures_FlatMap {

    public static void main(String[] args) throws InterruptedException {

        println("Step 1: Define a method which returns a Future");
        Function<String, Future<Integer>> donutStock = __ -> Future.of(() -> {
            Thread.sleep(500); // assume some long running database operation
            println("checking donut stock");
            return 10;
        });

        println("\nStep 2: Define another method which returns a Future");
        Function<Integer, Future<Boolean>> buyDonuts = quantity -> Future.of(() -> {
            // assume some long running database operation
            Thread.sleep(500);
            printf("buying %s donuts", quantity);
            return TRUE;
        });

        println("\nStep 3: Chaining Futures using flatMap");
        final Future<Boolean> buyingDonuts = donutStock.apply("vanilla donut").flatMap(buyDonuts::apply);
        Boolean isSuccess = buyingDonuts.await(5, SECONDS).get();
        printf("\nBuying vanilla donut was successful = %s", isSuccess);
        Thread.sleep(3000);
    }
}
