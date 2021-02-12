package com.allaboutjava.chap09;

import io.vavr.API;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Traversable;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;
import static java.lang.Integer.valueOf;
import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * D'après https://medium.com/musings-on-functional-programming/error-accumulation-with-collection-of-futures-82fb4da47466
 *
 * Of late, I’ve been dealing with problems related to accumulating errors
 * from multiple futures. Combinators like map, flatmap, sequence and traverse
 * have a fail-fast behaviour. While composing or sequencing multiple Futures,
 * they return the first failed Future they encounter. However, sometimes, we
 * want to find out what else could have gone wrong. I could describe the
 * problem in more detail, but as Linus Torvalds once said “Talk is cheap, show
 * me the code”.
 */
public class Tutorial_XX_Error_Accumulation_Futures {

    public static void main(String[] args) throws InterruptedException {

        // A motivating example

        // Given a list of strings representing integers, the code to find it’s sum would look like:
        final List<String> lst1 = List("1", "2", "3", "4", "5");
        final Number res1 = lst1.map(Integer::valueOf).sum();
        println(res1);

        // But, if a given string is not an integer, ‘toInt’ throws an exception:
        final List<String> lst2 = List("1", "2", "failure", "4", "5");
        try {
            lst2.map(Integer::valueOf).sum();
        } catch (NumberFormatException nfe) {
            println("Fails with NumberFormatException as expected...");
        }

        // If we want to concurrently convert each string to an integer and then find
        // the sum, we first wrap the call to toInt in a Future. We then get a list of
        // futures, use Future.sequence to get a future over a list of integers and map
        // over the future to find the sum of the list:
        final List<String> lst3 = List("1", "2", "3");
        final List<Future<Integer>> futInts1 = lst3.map(x -> Future(valueOf(x)));
        final Future<Number> futSum1 = Future.sequence(futInts1).map(Traversable::sum);
        final Future<Number> output1 = futSum1.await(1, SECONDS);
        println(output1.get());

        // A more general way to do this is to use Future.traverse as traverse basically
        // represents a map followed by a sequence :
        final List<String> lst4 = List("1", "2", "3");
        final Future<Number> futSum2 = Future.traverse(lst4, x -> Future(valueOf(x))).map(Traversable::sum);
        final Future<Number> output2 = futSum2.await(1, SECONDS);
        println(output2.get());

        // Fail-fast behaviour of Futures

        // But, there’s a problem with with what we’ve done. What if one of the strings is not an integer?
        final List<String> lst5 = List("1", "Failure", "3");
        try {
            final Future<Number> futSum3 = Future.traverse(lst5, x -> Future(valueOf(x))).map(Traversable::sum);
            final Future<Number> output3 = futSum3.await(1, SECONDS);
            println(output3.get());
        } catch (NumberFormatException nfe) {
            println("Fails with NumberFormatException as expected...");
        }


        /**
         * First attempt at error accumulation: map, recover, Try and Either
         *
         * - We lift a Future[T] to a Try[Future[T]] so that all futures are successful
         *   and we do not have to deal with failed futures. Future.map and
         *   Future.recover are our friends for this.
         *
         * - If a Future is successful, it’s type will be Future[Success[T]] and it fails,
         *   it’s type will be Future[Failure[Throwable]]. In either case, our Future
         *   will always be successful.
         *
         * - Once we have lifted all our Futures using Try, our error accumulation
         *   semantics are as follows:
         *
         * - If all the lifted Futures contain a Success, we return the result we want,
         *   the sum of all integers in our case.
         *
         * - If even lifted Future contains a Failure, we accumulate the exceptions
         *   from all Futures and return the List of exceptions.
         *
         * - The best type to model both the above behaviours is an
         *   Either[List[Throwable], Int]. We will return either the sum or the list of
         *   all exceptions.
         *
         * - Putting all this together, we get the following code:
         */

        List<String> lstKO = List("1", "failure1", "failure2", "failure3", "3");

        List<String> lstOK = List("1", "2", "3");

        Function<List<String>, Future<Serializable>> findSum = lst -> {
            final Future<Seq<Either<Throwable, Integer>>> futEither =
                    Future.traverse(lst, n -> Future(Try(() -> valueOf(n)).toEither()));

            final Future<Either<Seq<Throwable>, Seq<Integer>>> futureOfEither = futEither.map(Either::sequence);

            return futureOfEither.map(either ->
                    either.fold(
                            API::Left, // liste des erreurs accumulées
                            Traversable::sum // ce qu'on veut faire au final
                    ));
        };

        final Serializable outputKO = findSum.apply(lstKO).await(2, SECONDS).get();
        println(outputKO);

        final Serializable outputOK = findSum.apply(lstOK).await(2, SECONDS).get();
        println(outputOK);
    }
}

