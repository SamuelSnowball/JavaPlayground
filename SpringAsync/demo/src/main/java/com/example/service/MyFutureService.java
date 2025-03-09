package com.example.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

/*
 * For now just use this way to create a Future object, was looking for a simpler way.
 * First of all it's an interface so couldn't do it directly.
 */
@Service
public class MyFutureService {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Future<String> process() throws InterruptedException{
        return executor.submit(() -> {
            Thread.sleep(10000);
            return "Success";
        });
    }
}
