package com.lightbend.akka.sample;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.IOResult;
import akka.stream.Materializer;
import akka.stream.alpakka.geode.AkkaPdxSerializer;
import akka.stream.alpakka.geode.GeodeSettings;
import akka.stream.alpakka.geode.RegionSettings;
import akka.stream.alpakka.geode.internal.pdx.PdxDecoder;
import akka.stream.alpakka.geode.javadsl.MyReactiveGeode;
import akka.stream.alpakka.geode.javadsl.ReactiveGeode;
import akka.stream.javadsl.*;
import akka.util.ByteString;
import com.lightbend.akka.sample.entity.Person;
import com.lightbend.akka.sample.serializer.PersonPdxSerializer;
import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.query.QueryService;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;

import java.math.BigInteger;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static org.apache.geode.cache.RegionShortcut.REPLICATE;

public class AkkaGeode {

    public static void main(String[] argv) throws ExecutionException, InterruptedException {
        // Code here
        final ActorSystem system = ActorSystem.create("QuickStart");
        final Materializer materializer = ActorMaterializer.create(system);

        GeodeSettings settings = GeodeSettings.create("localhost", 10334)
                .withConfiguration((c)->c.setPoolIdleTimeout(10));
        MyReactiveGeode reactiveGeode =  new MyReactiveGeode(settings);

        RegionSettings<Integer, Person> personRegionSettings = new RegionSettings<>("personResult", Person::getId);
        Sink<Person, CompletionStage<Done>> sink = reactiveGeode.sink(personRegionSettings, new PersonPdxSerializer());

//        RunnableGraph<CompletionStage<Done>> runnableGraph = source
//                .toMat(sink, Keep.right());

        // Construct a new CQ.
        String cqName = "MyCq";
        String query = "SELECT * FROM /person";
        CompletionStage<Done> fut = reactiveGeode.continuousQuery(cqName, query, new PersonPdxSerializer())
                .toMat(sink, Keep.right()).run(materializer);

//                .runForeach(p -> {
//                    System.out.println(p.toString());
//                }, materializer);

        fut.toCompletableFuture().get();


//        // Construct a new CQ.
//        String query = "SELECT * FROM /person";
//        CompletionStage<Done> fut = reactiveGeode.query(query, new PersonPdxSerializer())
//                .runForeach(p -> {
//                    System.out.println(p.toString());
//                }, materializer);
//
//        fut.toCompletableFuture().get();

        System.out.println("end");
//        fut.thenRun(() -> system.terminate());
    }

}
