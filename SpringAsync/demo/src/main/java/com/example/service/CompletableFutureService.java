package com.example.service;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompletableFutureService {
    // Can be a supplier
    public Supplier<String> processOrder = () -> {
        String threadName = Thread.currentThread().getName();
		log.info("Starting processOrder in thread {}", threadName);

        try {
            Thread.sleep(2000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return "test";
    };

    // The rest need to be functions as to take in the previous result
    public Function<String, String> processPayment = (order) -> {
        String threadName = Thread.currentThread().getName();
		log.info("Starting processPayment in thread {}", threadName);

        try {
            Thread.sleep(2000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return "test";
    };

    public Function<String, String> processPaymentWhichFails = (a) -> {
        throw new RuntimeErrorException(null);
    };

    // The handle method always executes, regardless of if the previous CompletionStage had an exception
    public BiFunction<String, Throwable, String> handlePaymentError = (result, ex) -> {
        /*
        This is where we would check if there was an exception, but in this example
        lets always assume there is, so just throw a new one
        if(ex == null){

        }
        else {
        
        }
        */

        // Lets make the exceptionally() call execute.
        throw new RuntimeException();
    };

    public Function<Throwable, String> handleException = (ex) -> {
        return "Exceptionally() called, exception handled.";
    };

    public Function<String, String> processDelivery = (payment) -> {
        String threadName = Thread.currentThread().getName();
		log.info("Starting processDelivery in thread {}", threadName);

        try {
            Thread.sleep(2000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return "test";
    };
}