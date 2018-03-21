package com.lightbend.akka.sample.serializer;

import akka.stream.alpakka.geode.AkkaPdxSerializer;
import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxWriter;

public class StringPdxSerializer implements AkkaPdxSerializer<String> {
    @Override
    public Class<String> clazz() {
        return String.class;
    }

    @Override
    public boolean toData(Object o, PdxWriter out) {
        out.writeString("value", String.valueOf(o));
        return false;
    }

    @Override
    public Object fromData(Class<?> clazz, PdxReader in) {
        return in.readString("value");
    }
}
