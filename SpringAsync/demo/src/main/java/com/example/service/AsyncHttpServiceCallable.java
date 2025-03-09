package com.example.service;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;

import com.example.bean.MyRestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * This isn't really async as a plan RestClient is synchronous, just added a Thread.sleep.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AsyncHttpServiceCallable implements Callable<String> {

    private final MyRestClient client;

    @Override
    public String call() throws Exception {
        log.info("AsyncHttpServiceCallable call executing...");

        // Simulate a delayed HTTP request

        Thread.sleep(10000);

        String result = client.getClient().get()
                .uri("https://google.com")
                .retrieve()
                .body(String.class);

        log.info(result);

        log.info("AsyncHttpServiceCallable call finished.");
        return result;
    }

}
