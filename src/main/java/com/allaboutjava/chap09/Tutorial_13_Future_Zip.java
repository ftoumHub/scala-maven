package com.allaboutjava.chap09;

import io.vavr.Tuple2;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;

import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;
import static io.vavr.Predicates.instanceOf;
import static java.lang.Thread.sleep;

public class Tutorial_13_Future_Zip {

    public static void main(String[] args) throws InterruptedException {

        println("Step 1: Define a method which returns a Future Option");
        Function<String, Future<Option<Integer>>> donutStock = donut -> Future.of(() -> {
            // assume some long running database operation
            sleep(500);
            println("checking donut stock");
            return donut.equals("vanilla donut") ? Some(10) : None();
        });

        println("\nStep 2: Define a method which returns a Future Double for donut price");
        final Future<Double> donutPrice = Future.successful(3.25);

        println("\nStep 3: Zip the values of the first future with the second future");
        final Future<Tuple2<Option<Integer>, Double>> donutStockAndPriceOperation = donutStock.apply("vanilla donut").zip(donutPrice);
        donutStockAndPriceOperation
                .onComplete(response ->
                        Match(response).of(
                                Case($Success($()), results -> run(() -> {
                                    printf("Results %s", results);
                                })),
                                Case($Failure($()), e -> run(() ->
                                        printf("Error processing future operations, error = %s", e.getMessage())))
                        ));

        sleep(3000);
    }
}
