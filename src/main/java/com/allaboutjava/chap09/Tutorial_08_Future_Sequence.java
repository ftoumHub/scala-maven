package com.allaboutjava.chap09;

import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.Tuple.empty;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Thread.sleep;

public class Tutorial_08_Future_Sequence {

    public static void main(String[] args) throws InterruptedException {

        println("Step 1: Define a method which returns a Future Option");
        final Function<String, Future<Option<Integer>>> donutStock = donut -> Future.of(() -> {
            println("\nchecking donut stock ... sleep for 2 seconds");
            sleep(2000);
            return donut.equals("vanilla donut") ? Some(10) : None();
        });

        println("\nStep 2: Define another method which returns a Future<Boolean>");
        final Function<Integer, Future<Boolean>> buyDonuts = quantity -> Future.of(() -> {
            printf("buying %s donuts ... sleep for 3 seconds", quantity);
            sleep(3000);
            return (quantity > 0) ? TRUE : FALSE;
        });

        println("\nStep 3: Define another method for processing payments and returns a Future[Unit]");
        final Supplier<Future<Tuple>> processPayment = () -> Future.of(() -> {
            println("\nprocessPayment ... sleep for 1 second");
            sleep(1000);
            return empty();
        });

        println("\nStep 4: Combine future operations into a List");
        final List<Future<?>> futureOperations =
                List(donutStock.apply("vanilla donut"), buyDonuts.apply(10), processPayment.get());

        println("\nStep 5: Call Future.sequence to run the future operations in parallel");
        final Function<Seq<Object>, Void> success = results -> run(() -> {
            results.length();
            printf("Results %s", results);
        });

        final Function<Throwable, Void> failure = e -> run(() ->
                printf("Error processing future operations, error = %s", e.getMessage()));

        final Consumer<Try<Seq<Object>>> tryConsumer = response ->
            Match(response).of(
                    Case($Success($()), success),
                    Case($Failure($()), failure));

        Future.sequence(futureOperations).onComplete(tryConsumer);

        sleep(10000);
    }
}
