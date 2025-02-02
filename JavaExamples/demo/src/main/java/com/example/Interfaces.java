package com.example;

import lombok.extern.slf4j.Slf4j;

/*
 * Introduced in Java 8, you can define static methods in interfaces. These methods can provide functionality and cannot be overridden.
 * They are called as you would expect, using the class name followed by the method name.
 */
@Slf4j
public class Interfaces {

    private static String myVariable = "From outer";

    public static void main(String[] args) {
        System.out.println("Hello world!");

        // Example 1, call a static method directly from an interface
        log.info(AnimalInterface.getSpecises());

        log.info("Args.length: " + args.length);
        var var = args.length > 50000 ? new Animal() : new Dog();

        log.info("Class is: " + var.getClass());

//        var z = new Zoo();


    }

    interface AnimalInterface {
        static String getSpecises(){
            return "Bird";
        }
        default String defaultMethod(){
            return "";
        }
    }

    static class Zoo implements AnimalInterface {
        public String printAnimals(){
            return "";
        }

        public void staticExamples(){
            log.info(myVariable);
        }
    }
} 

class Animal {

}

class Dog {

}