package com.allaboutjava.chap09;

import io.vavr.Tuple2;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;

import java.util.function.BiFunction;
import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;
import static java.lang.Thread.sleep;

public class Tutorial_14_Future_ZipWith {

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

        println("\nStep 3: Define a value function to convert Tuple (Option[Int], Double) to Tuple (Int, Double)");
        BiFunction<Option<Integer>, Double, Tuple2<Integer, Double>> qtyAndPriceF = (opt, d) -> Tuple(opt.getOrElse(0), d);

        println("\nStep 4: Call Future.zipWith and pass-through function qtyAndPriceF");
        final Future<Tuple2<Integer, Double>> donutStockAndPriceOperation = donutStock.apply("vanilla donut")
                .zipWith(donutPrice, qtyAndPriceF);
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
