package com.lightbend.akka.sample;

import com.lightbend.akka.sample.entity.Person;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;

import java.util.Scanner;

public class GeodePutter {

    public static void main(String[] argv) {

        // Create the cache which causes the cache-xml-file to be parsed
        ClientCache cache = new ClientCacheFactory()
                .set("name", "CqClient")
                .set("cache-xml-file", "xml/cache.xml")
                .setPdxPersistent(true)
                .setPdxSerializer(new ReflectionBasedAutoSerializer("com.lightbend.akka.sample.entity.Person"))
                .create();


        // Get the exampleRegion
        Region<Integer, Person> exampleRegion = cache.getRegion("person");
        System.out.println("Example region \"" + exampleRegion.getFullPath() + "\" created in cache.");
        System.out.println("plese input person info.format:[id name note]");

        Scanner scan = new Scanner(System.in);
        Integer id = scan.nextInt();
        String name = scan.next();
        String note = scan.next();
        Person person1 = new Person(id,name, note);
        exampleRegion.put(id, person1);
    }

}
