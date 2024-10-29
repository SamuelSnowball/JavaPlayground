package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class DummyClass {
    private String name;

    public DummyClass(){}

    public DummyClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
