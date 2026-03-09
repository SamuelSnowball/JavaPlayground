package com.example.service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
See corresponding unit test file for example logs and notes.

### Notes ###
Supplier<String> supplier = () -> {
	return "This is sync unless executed otherwise..! {} " + Thread.currentThread().getName();
};

Executing it sync:
	String result = supplier.get();

Executing it async:
	CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier);
*/
@Slf4j
@RequiredArgsConstructor
public class CompletableFutureService {

	private final ExampleService exampleService;

	/*
	 * Demo of supplyAsync, run some code asynchonously and print the threads name
	 * that is executing it.
	 * 
	 * Use get() to block the calling thread until completion.
	 * 
	 * Also shows separate Supplier syntax VS defining it inline within the
	 * supplyAsync call.
	 */
	public void supplyAsyncDemo() throws Exception {

		log.info("supplyAsyncDemo executed on thread: {}", Thread.currentThread().getName());

		Supplier<String> supplier = () -> {
			return "supplier executed on thread: " + Thread.currentThread().getName();
		};

		CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier);

		String result = future.get();
		log.info("Async task has completed, result is: {}", result);
	}

	/*
	 * Demo of synchronous continuation methods (vs the async variant), such as:
	 * thenApply, thenAccept, thenRun.
	 * 
	 * These synchronous methods will run on the thread that completes the previous
	 * stage. It is not guaranteed to be the same thread that ran runAsync.
	 * 
	 * That might be:
	 * the async worker thread
	 * the thread calling .get()
	 * the thread calling .join()
	 * a web server thread
	 * the main thread in a test
	 * 
	 * It is not guaranteed to be the same thread that ran runAsync.
	 */
	public void synchronousContinuationMethods() throws Exception {

		log.info("synchronousContinuationMethods executed on thread: {}", Thread.currentThread().getName());

		// The async task and the function will be executed in the same thread
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> { // Run this code asynchronously
			log.info("Task told to run async, executed on thread: " + Thread.currentThread().getName());
		}).thenRun(() -> {
			// No guarantee this runs on the same thread as the previous stage, but it might
			log.info("Task told to run sync, executed on thread: " + Thread.currentThread().getName()); 
		});

		// Block until the future is complete
		future.get();
	}

	/*
	 * Demo of asynchronous continuation methods, such as thenApplyAsync,
	 * thenAcceptAsync, thenRunAsync.
	 * 
	 * Unlike the above example, these asynchronous methods will run on an EXECUTOR
	 * THREAD, but there is no guarantee which thread that will be.
	 * 
	 * If you don't provide one, it will use the: ForkJoinPool.commonPool.
	 * So the continuation will run on:
	 * - a ForkJoinPool worker thread
	 * - or a thread from your custom executor (if you pass one)
	 * 
	 * It will not run on:
	 * - the thread calling .get()
	 * - the thread calling .join()
	 * - a web server thread
	 * - the main thread in a test
	 */
	public void asynchronousContinuationMethods() throws Exception {

		log.info("asynchronousContinuationMethods executed on thread: {}", Thread.currentThread().getName());

		// The async task and the function will be executed in the same thread
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> { // Run this code asynchronously
			log.info("Task told to run async, executed on thread: " + Thread.currentThread().getName());
		}).thenRunAsync(() -> { // And run this code asynchronously as well
			log.info("Task told to run sync, executed on thread: " + Thread.currentThread().getName()); // Will always run on an executor thread
		});

		// Block until the future is complete
		future.get();
	}

	/*
	 * An example which uses sequential async tasks.
	 * 
	 * The the output of one task is the input of the next, which means each task
	 * has to wait for the previous one to complete before it can start. So the total
	 * time taken will be the sum of all the tasks time, which is 15 seconds in this case.
	 */
	public void sequentialAsyncTasksExample() throws Exception {

		log.info("sequentialAsyncTasksExample executed on thread: {}", Thread.currentThread().getName());

		CompletableFuture<String> deliveryFutureResult = CompletableFuture
				.supplyAsync(exampleService.processOrder) // Supplier
				.thenApplyAsync(exampleService.processPayment) // Function
				.thenApplyAsync(exampleService.processDelivery); // Function

		log.info("sequentialAsyncTasksExample function continuing doing stuff.. executed on thread: {}", Thread.currentThread().getName());
		
		// Could do some other stuff here while the async tasks are running
		// Could do some other stuff here while the async tasks are running
		// Could do some other stuff here while the async tasks are running

		// Block until the future is complete and get the result
		String deliveryResult = deliveryFutureResult.get();
		log.info("All async tasks have completed, final result is: {}", deliveryResult);
	}

	/*
	 * A realistic example for a website which fires off multiple tasks and waits for them all to complete.
	 * User visits page and we want to show them recommendations, trending items and latest deals.
	 * Each task takes 5 seconds, but they run in parallel so the total time taken is around 5 seconds, not 15 seconds.
	 */
	public void parallelAsyncTasksExample() throws Exception {

		log.info("parallelAsyncTasksExample executed on thread: {}", Thread.currentThread().getName());

		CompletableFuture<String> recommendationsFuture = CompletableFuture.supplyAsync(exampleService.getRecommendations);
		CompletableFuture<String> trendingItemsFuture = CompletableFuture.supplyAsync(exampleService.getTrendingItems);
		CompletableFuture<String> latestDealsFuture = CompletableFuture.supplyAsync(exampleService.getLatestDeals);

		log.info("parallelAsyncTasksExample function continuing doing stuff.. executed on thread: {}", Thread.currentThread().getName());
		
		// Could do some other stuff here while the async tasks are running
		// Could do some other stuff here while the async tasks are running
		// Could do some other stuff here while the async tasks are running

		CompletableFuture<Void> allFutures = CompletableFuture.allOf(recommendationsFuture, trendingItemsFuture, latestDealsFuture);
		allFutures.get(); // Block until all futures are complete

		// Get the return values
		String recommendations = recommendationsFuture.join(); 
		String trendingItems = trendingItemsFuture.join(); 
		String latestDeals = latestDealsFuture.join(); 
		log.info("Recommendations: {}", recommendations); 
		log.info("Trending items: {}", trendingItems); 
		log.info("Latest deals: {}", latestDeals);

		log.info("All async tasks have completed!");
	}

	/*
	 * Note: Handle() is called regardless of whether an exception was thrown or not, 
	 * and it receives both the result and the exception (if one was thrown) as parameters.
	 * 
	 * Mimic some exceptions being thrown, in this example we even intentionally throw
	 * an exception from the handleError method. Which will trigger the exceptionally
	 * call.
	 */
	public void asyncExceptionExample() throws Exception {

		log.info("asyncExceptionExample executed on thread: {}", Thread.currentThread().getName());

		CompletableFuture<String> future = CompletableFuture
				.supplyAsync(exampleService.processOrder)
				.thenApplyAsync(exampleService.processPaymentWhichFails)
				.thenApplyAsync(exampleService.processDelivery) // never reaches here
				.handle(exampleService.handle) // Will get called regardless of if there was an exception
				.exceptionally(exampleService.exceptionallyHandleError);

		log.info("Final result is: {}", future.get());
	}

}
