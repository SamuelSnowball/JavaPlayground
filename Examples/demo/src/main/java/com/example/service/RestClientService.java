package com.example.service;

import java.util.concurrent.Callable;

import org.springframework.web.client.RestClient;

import lombok.extern.slf4j.Slf4j;

/*
Shows how Springs RestClient is synchronous.
*/
@Slf4j
public class RestClientService {

    public void callGoogle() throws Exception {
        log.info("Starting callGoogleWrapper on thread: " + Thread.currentThread().getName());
        callGoogle.call(); // Just discard the return value
        log.info("Finished executing callGoogleWrapper on thread: " + Thread.currentThread().getName());
    }

    // Using Callable so we can return a result
    private Callable<String> callGoogle = () -> {
        log.info("Starting callGoogle on thread: " + Thread.currentThread().getName());

        try {
            Thread.sleep(5000); // Simulate a long running API call
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return getClient().get()
                .uri("https://google.com")
                .retrieve()
                .body(String.class);
    };

    private RestClient getClient() {
        // Use spring HTTP client
        return RestClient.builder()
                .baseUrl("https://google.com")
                .build();
    }
}
