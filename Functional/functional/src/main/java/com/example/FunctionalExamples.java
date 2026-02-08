package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/functionalExamples")
public class FunctionalExamples {

    @GetMapping
    void supplyAsyncSyntax() {
        // If using a Supplier, we can pass it directly to supplyAsync
        Supplier<String> supplierExample = () -> "Hello world";
        CompletableFuture.supplyAsync(supplierExample);

        // If using a variable, need to wrap it in a lambda
        String variableExample = "Hello world";
        CompletableFuture.supplyAsync(() -> variableExample);
    }


    // Syntax is:
    Supplier<String> example = () -> {
        return "Hello world";
    };

    // NOT
    // Supplier<String> example2(){
    //     return "Hello world";
    // }
    // As it needs to return a Supplier, not a String.

    // Unless you do
    Supplier<String> example3() {
        return () -> "This is a bit redundant but it works";
    }

}
