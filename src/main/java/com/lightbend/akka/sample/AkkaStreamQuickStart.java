package com.lightbend.akka.sample;

import akka.stream.*;
import akka.stream.javadsl.*;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.util.ByteString;

import java.nio.file.Paths;
import java.math.BigInteger;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;

public class AkkaStreamQuickStart {
    public static void main(String[] argv) {
        // Code here
        final ActorSystem system = ActorSystem.create("QuickStart");
        final Materializer materializer = ActorMaterializer.create(system);
        final Source<Integer, NotUsed> source = Source.range(1, 100);
        final CompletionStage<Done> done =
                source.runForeach(i -> System.out.println(i), materializer);

        final Source<BigInteger, NotUsed> factorials =
                source
                        .scan(BigInteger.ONE, (acc, next) -> acc.multiply(BigInteger.valueOf(next)));
        final CompletionStage<IOResult> result =
                factorials
                        .map(num -> ByteString.fromString(num.toString() + "\n"))
                        .runWith(FileIO.toPath(Paths.get("factorials.txt")), materializer);

        //done.thenRun(() -> system.terminate());
    }

}
