package com.allaboutjava.chap09;

import io.vavr.concurrent.Future;

import java.util.function.Function;

import static io.vavr.API.printf;
import static io.vavr.API.println;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Method with future as return type.
 */
public class Tutorial_01_Function_With_Future_Return_Type {

    public static void main(String[] args) {

        println("Step 1: Define a function which returns a Future");
        Function<String, Future<Integer>> donutStock = __ ->  Future.of(() -> {
            // assume some long running database operation
            println("checking donut stock");
            return 10;
        });

        println("\nStep 2: Call method which returns a Future");
        // ici l'appel est bloquant
        Integer vanillaDonutStock = donutStock.apply("vanilla donut").await(5, SECONDS).get();
        printf("Stock of vanilla donut = %s", vanillaDonutStock);
    }
}
