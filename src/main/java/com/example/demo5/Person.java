package com.example.insecuredeserializationdemo.model;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;

    // Getters and setters

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}