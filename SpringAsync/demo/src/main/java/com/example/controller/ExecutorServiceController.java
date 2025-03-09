package com.example.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.classes.Util;
import com.example.service.AsyncHttpServiceCallable;
import com.example.service.AsyncServiceRunnable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("executorServiceController")
@Slf4j
@RequiredArgsConstructor
public class ExecutorServiceController {

	private final AsyncHttpServiceCallable callableService;

	// Submit a runnable to an executorService
	// This is going to start the task via the new thread, but return from the
	// endpoint instantly as we aren't blocking
	// Also will each invocation will leak an executorService? Not sure, it's scoped
	// to the block.
	// True but the thread might just be kept alive even if it's finished?
	// With shutdown() it doesn't block the main thread, I thought it might
	@GetMapping("/example1")
	public String example1() throws InterruptedException {
		// Setup / logging
		Instant start = Util.startLog("example1");

		// Logic
		ExecutorService es = Executors.newFixedThreadPool(3);
		es.submit(new AsyncServiceRunnable());
		// es.shutdown();

		// Response / logging
		Util.endLog("example1", start);

		return "Success";
	}

	/*
	 * Submit a callable to an executorService
	 * Call a method which in turn makes a (fake thread.sleep) 10 second synchronous
	 * HTTP call somewhere,
	 * We expect this controller method to return immediatley, and the outbound HTTP
	 * request to complete afterwards.
	 * 
	 * Expected logs
	 * Starting example2
	 * Completed example2
	 * AsyncHttpServiceCallable call executing
	 * AsyncHttpServiceCallable call finished
	 * 
	 * (roughly in order, the
	 * "Completed example2 / AsyncHttpServiceCallable call" executing - could
	 * be other way around)
	 */
	@GetMapping("/example2")
	public String example2() throws InterruptedException {
		// Setup / logging
		Instant start = Util.startLog("example2");

		// Logic
		ExecutorService es = Executors.newFixedThreadPool(1);
		es.submit(callableService);
		es.shutdown();

		// Response / logging
		Util.endLog("example2", start);

		return "Success";
	}

	/*
	 * Same as above method, but make the controller method will block until the request spawned from the
	 * executorService completes or timesout.
	 * 
	 * We provide the timeout as a String via queryParameters.
	 * 
	 * Call a method which in turn makes a (fake thread.sleep) 10 second synchronous
	 * HTTP call somewhere,
	 * We expect this controller method to return after the outbound HTTP request
	 * completes.
	 * 
	 * Expected logs for a timeout of 5 seconds
	 * Starting example3 
	 * AsyncHttpServiceCallable call executing...
	 * Completed example3
	 * Controller returns: Tasks failed to execute in time
	 * 
	 * Expected logs for a timeout of > 10 seconds
	 * Starting example3 
	 * AsyncHttpServiceCallable call executing...
	 * AsyncHttpServiceCallable call finished.
	 * Completed example3
	 * Controller returns: Success
	 */
	@GetMapping("/example3")
	public String example3(@RequestParam("timeout") String timeout) throws InterruptedException {
		// Setup / logging
		Instant start = Util.startLog("example3");

		// Logic
		ExecutorService es = Executors.newFixedThreadPool(1);
		es.submit(callableService);
		es.shutdown();
		boolean tasksCompletedSuccessfully = es.awaitTermination(Long.valueOf(timeout), TimeUnit.SECONDS);

		// Response / logging
		Util.endLog("example3", start);

		if (tasksCompletedSuccessfully) {
			return "Success";
		} else {
			return "Tasks failed to execute in time";
		}
	}
}