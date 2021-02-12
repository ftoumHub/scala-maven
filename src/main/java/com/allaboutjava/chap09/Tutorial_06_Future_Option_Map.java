package com.allaboutjava.chap09;

import io.vavr.Tuple;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;

import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.Tuple.empty;
import static java.lang.Boolean.TRUE;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Tutorial_06_Future_Option_Map {

    public static void main(String[] args) throws InterruptedException {

        println("Step 1: Define a method which returns a Future Option");
        Function<String, Future<Option<Integer>>> donutStock = donut -> Future.of(() -> {
            Thread.sleep(500); // assume some long running database operation
            println("checking donut stock");
            return donut.equals("vanilla donut") ? Some(10) : None();
        });

        println("\nStep 2: Access value returned by future using map() method");
        donutStock.apply("vanilla donut")
                .map(someQty -> {
                    printf("Buying %s vanilla donuts", someQty.getOrElse(0));
                    return empty();
                });

        Thread.sleep(3000);
    }
}
