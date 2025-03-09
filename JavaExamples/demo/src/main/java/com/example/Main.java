package com.example;

/*
 * Example showing that because my interface isn't functional, I can't use it within a Lambda.
 * However I can still use an anonymous inner class and provide the required method definitions.
 * 
 * Note, we are using new on an interface, just providing an implementation straight away.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Test t = new Test();

        // Method 1 expects a MyInterface parameter, but because that interface isn't
        // functional, we can't use lambda syntax
        t.methodThatAcceptsNonFunctionalInterface(new MyNonFunctionalInterface() {

            @Override
            public void method1() {
                throw new UnsupportedOperationException("Unimplemented method 'method1'");
            }

            @Override
            public void method2() {
                throw new UnsupportedOperationException("Unimplemented method 'method2'");
            }

        });

        // Lambda expression is allowed here
        t.methodThatAcceptsFunctionalInterface(() -> {

        });
    }
}

interface MyNonFunctionalInterface {
    void method1();
    void method2();
}

interface MyFunctionalInterface {
    void method1();
}

class Test {
    public void methodThatAcceptsNonFunctionalInterface(MyNonFunctionalInterface i) {

    }

    public void methodThatAcceptsFunctionalInterface(MyFunctionalInterface i) {

    }
}