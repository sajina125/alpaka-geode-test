package com.lightbend.akka.sample.entity;

import java.util.Date;

public class Person {

    public Person() {

    }

    public Person(Integer id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;
    }

    private Integer id;
    private String name;
    private String note;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note=" + note +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
