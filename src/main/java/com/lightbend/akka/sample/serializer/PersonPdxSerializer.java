package com.lightbend.akka.sample.serializer;

import akka.stream.alpakka.geode.AkkaPdxSerializer;
import com.lightbend.akka.sample.entity.Person;
import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxWriter;

import java.util.Date;

public class PersonPdxSerializer implements AkkaPdxSerializer<Person> {

    @Override
    public Class<Person> clazz() {
        return Person.class;
    }

    @Override
    public boolean toData(Object o, PdxWriter out) {
        if(o instanceof Person){
            Person p = (Person)o;
            out.writeInt("id", p.getId());
            out.writeString("name", p.getName());
            out.writeString("note",p.getNote());
            return true;
        }
        return false;
    }

    @Override
    public Object fromData(Class<?> clazz, PdxReader in) {
        int id = Integer.valueOf(in.readObject("id").toString());
        String name = in.readString("name");
        String note = in.readString("note");
        return new Person(id, name, note);
    }
}