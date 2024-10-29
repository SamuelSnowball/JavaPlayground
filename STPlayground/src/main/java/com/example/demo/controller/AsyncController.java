package com.example.demo.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * Demoing async concepts
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	private static final Logger logger = LoggerFactory.getLogger(AsyncController.class);

	@Async
	@GetMapping("/asynchelloworld")
	public CompletableFuture<String> asynchelloworld(@RequestParam(value = "name", defaultValue = "World") String name) {

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("GET /asynchelloworld");
		return CompletableFuture.completedFuture("Task Completed");
	}
    
}
