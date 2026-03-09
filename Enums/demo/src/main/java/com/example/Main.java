package com.example;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    private volatile Integer counter = 0;

    class MyInner<T> {

        private T myValue;

        protected MyInner(T myValue){
            this.myValue = myValue;
        }

        public T getMyValue(){
            return myValue;
        }
    }

    class MyInner2 {

        public <T> void myMethod(T input){

        }

        public <T> T echo(T input) {
            return input;
        }

    }

    public static void main(String[] args) {

        
        String a = "a";
        String a2 = "a";

        log.info("{}", a == a2);
        log.info("{}", a.equals(a2));

        int[] arr = {0,1,2};

        List<Integer> list = Arrays.asList(0,1,2);


        /*

        // Note, cannot instantiate enum using new()
        // MyEnum attempt = new MyEnum();

        MyEnum myEnumValue = MyEnum.VALUE2;
        log.info("Enum value: {}", myEnumValue.getValue());

        // Use of .values() to iterate over all enum constants
        Arrays.asList(MyEnum.values()).stream()
            .filter(e -> e.getValue().contains("1"))
            .forEach(e -> log.info("Filtered enum value: {}", e.getValue()));
        */
    }
}

enum MyEnum {
    VALUE1("Value 1"),
    VALUE2("Value 2"),
    VALUE3("Value 3");

    private final String value;

    private MyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}