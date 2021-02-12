package com.allaboutjava.chap09;

import io.vavr.concurrent.Future;
import io.vavr.control.Option;

import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.API.None;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Tutorial_07_Composing_Futures {

    public static void main(String[] args) throws InterruptedException {

        println("Step 1: Define a method which returns a Future Option");
        Function<String, Future<Option<Integer>>> donutStock = donut -> Future.of(() -> {
            Thread.sleep(500); // assume some long running database operation
            println("checking donut stock");
            return donut.equals("vanilla donut") ? Some(10) : None();
        });

        println("\nStep 2: Define another method which returns a Future");
        Function<Integer, Future<Boolean>> buyDonuts = quantity -> Future.of(() -> {
            // assume some long running database operation
            Thread.sleep(500);
            printf("buying %s donuts", quantity);
            return (quantity > 0) ? TRUE : FALSE;
        });

        println("\nStep 3: Calling map() method over multiple futures");
        final Future<Future<Boolean>> resultFromMap = donutStock.apply("vanilla donut")
                .map(someQty -> buyDonuts.apply(someQty.getOrElse(0)));
        Thread.sleep(1000);

        println("\nStep 4: Calling flatMap() method over multiple futures");
        final Future<Boolean> resultFromFlatMap = donutStock.apply("vanilla donut")
                .flatMap(someQty -> buyDonuts.apply(someQty.getOrElse(0)));
        Thread.sleep(1000);
    }
}
