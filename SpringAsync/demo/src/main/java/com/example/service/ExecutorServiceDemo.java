package com.example.service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExecutorServiceDemo {

	// Submit a runnable to an executorService
	// This is going to start the task via the new thread, but return from the
	// endpoint instantly as we aren't blocking
	// Also will each invocation will leak an executorService? Not sure, it's scoped
	// to the block.
	// True but the thread might just be kept alive even if it's finished?
	// With shutdown() it doesn't block the main thread, I thought it might
	public String example1() throws InterruptedException {

		// Logic
		ExecutorService es = Executors.newFixedThreadPool(3);
		es.submit(new AsyncServiceRunnable());
		// es.shutdown();


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
	public String example2() throws InterruptedException {

		// Logic
		ExecutorService es = Executors.newFixedThreadPool(1);
		es.submit(new CallableService());
		es.shutdown();

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
	public String example3(String timeout) throws InterruptedException {
		// Logic
		ExecutorService es = Executors.newFixedThreadPool(1);
		es.submit(new CallableService());
		es.shutdown();
		boolean tasksCompletedSuccessfully = es.awaitTermination(Long.valueOf(timeout), TimeUnit.SECONDS);

		if (tasksCompletedSuccessfully) {
			return "Success";
		} else {
			return "Tasks failed to execute in time";
		}
	}

	// Just a simple callable for testing
	// Can only be run via an ExecutorService, not directly via a Thread like a Runnable
	class CallableService implements Callable<String> {

		@Override
		public String call() throws Exception {
			log.info("CallableService call executing...");
			Thread.sleep(5000);
			log.info("CallableService call finished.");
			return "Success";
		}

	}
}
