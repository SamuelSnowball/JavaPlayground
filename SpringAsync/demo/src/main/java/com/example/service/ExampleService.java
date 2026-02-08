package com.example.service;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/*
Using Thread.sleep to simulate a long running task, 
so we can see the benefits of async code.
*/
@Service
@Slf4j
public class ExampleService {
    
    public Supplier<String> processOrder = () -> {
		log.info("Starting processOrder in thread {}", Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return "Order processed";
    };

    // Using functions as to take in the previous result
    public Function<String, String> processPayment = (order) -> {
		log.info("Starting processPayment in thread {}", Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return "Payment processed";
    };

    public Function<String, String> processPaymentWhichFails = (order) -> {
        throw new RuntimeException("Payment failed");
    };

    public Function<String, String> processDelivery = (payment) -> {
		log.info("Starting processDelivery in thread {}", Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return "Delivery processed";
    };

    /*
    .handle() expects:
    1. the result of the previous stage
    2. an exception (if one occurred)

    If the previous stage was successful, the result will be populated.
    Otherwise if there was an exception, result will be null and exception will be populated.
    */
    public BiFunction<String, Throwable, String> handle = (result, exception) -> {
        // Lets see what failed
        // Need to call .toString on the exception otherwise it appears blank..
        log.info("Handling error, result: {}, exception: {}", result, exception.toString());

        // Do this to trigger the exceptionally block
        throw new RuntimeException("Error handling failed");
    };

    public Function<Throwable, String> exceptionallyHandleError = (exception) -> {
        log.info("Exceptionally() called, exception handled.");
        return "Default value";
    };

    // ###
    // The below methods are for parallelAsyncTasksExample
    // ###

    public Supplier<String> getRecommendations = () -> {
		log.info("Starting getRecommendations in thread {}", Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return "Some recommendations...";
    };

    public Supplier<String> getTrendingItems = () -> {
		log.info("Starting getTrendingItems in thread {}", Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return "Some trending items...";
    };

    public Supplier<String> getLatestDeals = () -> {
		log.info("Starting getLatestDeals in thread {}", Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return "Some latest deals...";
    };

}