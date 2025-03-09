package com.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.classes.Util;
import com.example.service.CompletableFutureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("completableFutureController")
@Slf4j
@RequiredArgsConstructor
public class CompletableFutureController {

    private final CompletableFutureService completableFutureService;

	/*
	 * Create a supplier, which runs via an async method, it will print out the threads name that is executing it
	 * We use get() to block the calling thread until completion.
	 * 
	 * This demo shows that by using supplyAsync, the task will be ran on a new thread.
	 * 
	 * Logs:
	 * Starting asyncExample7 in thread http-nio-8080-exec-1
	 * Supplier running in thread: ForkJoinPool.commonPool-worker-1
	 * Future completed, result is Example
	 * Completed asyncExample7 in thread http-nio-8080-exec-1, execution time 0s 
	 */
	@GetMapping("/example1")
	public String example1() throws Exception {
		// Setup / logging
		Instant start = Util.startLog("example1");

		// Logic
		Supplier<String> supplier = () -> { 
			String supplierThreadName = Thread.currentThread().getName();
			log.info("Supplier running in thread: {}", supplierThreadName);

			return "Example";
		};

		CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier);
		String result = future.get();
		log.info("Future completed, result is {}", result);

		// Response / logging
		Util.endLog("example1", start);

		return "Success";
	}

    /*
     * Demo showing how subsequent sync calls from .supplyAsync will run in the original thread that the supplyAsync task used
     * 
     * Logs:
     * Starting example2 in thread http-nio-8080-exec-4
     * Supplier running in thread: ForkJoinPool.commonPool-worker-2
     * Function running in thread: ForkJoinPool.commonPool-worker-2
     * Future completed, result is: Example from function
     * Completed example2 in thread http-nio-8080-exec-4, execution time 0s
     */
    @GetMapping("/example2")
	public String example2() throws Exception {
		// Setup / logging
		Instant start = Util.startLog("example2");

		// Logic
		Supplier<String> supplier = () -> { 
			String supplierThreadName = Thread.currentThread().getName();
			log.info("Supplier running in thread: {}", supplierThreadName);

			return "Example";
		};

        // A function takes in a value and returns a result, concat something onto the return value from the supplier
        Function<String, String> function = (String a) -> {
            String functionThreadName = Thread.currentThread().getName();
			log.info("Function running in thread: {}", functionThreadName);

            return a + " from function";
        }; 

        // The supplier and function will be executed in the same thread
		CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier).thenApply(function);

		String result = future.get();
		log.info("Future completed, result is: {}", result);

		// Response / logging
		Util.endLog("example2", start);

		return "Success";
	}

    /*
     * Demo using multiple supplyAsync calls, in this example as the first async call completes very quickly, the second async
     * call gets executed in the same thread. If the async call takes longer to complete, the subsequent calls could run in a new thread,
     * see next example.
     * 
     * Starting example3 in thread http-nio-8080-exec-2
     * Supplier running in thread: ForkJoinPool.commonPool-worker-3
     * Function running in thread: ForkJoinPool.commonPool-worker-3
     * Future completed, result is: Example from function
     * Completed example3 in thread http-nio-8080-exec-2, execution time 0s
     */
    @GetMapping("/example3")
	public String example3() throws Exception {
		// Setup / logging
		Instant start = Util.startLog("example3");

		// Logic
		Supplier<String> supplier = () -> { 
			String supplierThreadName = Thread.currentThread().getName();
			log.info("Supplier running in thread: {}", supplierThreadName);

			return "Example";
		};

        // A function takes in a value and returns a result, concat something onto the return value from the supplier
        Function<String, String> function = (String a) -> {
            String functionThreadName = Thread.currentThread().getName();
			log.info("Function running in thread: {}", functionThreadName);

            return a + " from function";
        }; 

        // The supplier and function might be executed in different threads
        // The function is dependant on the supplier completing
		CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier).thenApplyAsync(function);

		String result = future.get();
		log.info("Future completed, result is: {}", result);

		// Response / logging
		Util.endLog("example3", start);

		return "Success";
	}

    /*
     * Demo using multiple supplyAsync calls, in this example as the first async call completes slowly, the second async
     * call might get executed in a different thread. 
     * 
     * In the first execution they ran in the same thread, in the second they are executed in different threads.
     * 
     * Starting example4 in thread http-nio-8080-exec-1
     * Supplier running in thread: ForkJoinPool.commonPool-worker-5
     * Function running in thread: ForkJoinPool.commonPool-worker-5
     * Future completed, result is: Example from function
     * Completed example4 in thread http-nio-8080-exec-1, execution time 2s
     * 
     * Starting example4 in thread http-nio-8080-exec-4
     * Supplier running in thread: ForkJoinPool.commonPool-worker-5
     * Function running in thread: ForkJoinPool.commonPool-worker-6
     * Future completed, result is: Example from function
     * Completed example4 in thread http-nio-8080-exec-4, execution time 2s
     */
    @GetMapping("/example4")
	public String example4() throws Exception {
		// Setup / logging
		Instant start = Util.startLog("example4");

		// Logic
		Supplier<String> supplier = () -> { 
			String supplierThreadName = Thread.currentThread().getName();
			log.info("Supplier running in thread: {}", supplierThreadName);

            try {
                Thread.sleep(2000);
            } catch(InterruptedException e){

            }

			return "Example";
		};

        // A function takes in a value and returns a result, concat something onto the return value from the supplier
        Function<String, String> function = (String a) -> {
            String functionThreadName = Thread.currentThread().getName();
			log.info("Function running in thread: {}", functionThreadName);

            return a + " from function";
        }; 

        // The supplier and function might be executed in different threads
        // The function is dependant on the supplier completing
		CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier).thenApplyAsync(function);

		String result = future.get();
		log.info("Future completed, result is: {}", result);

		// Response / logging
		Util.endLog("example4", start);

		return "Success";
	}

    /*
     * More descriptive CompletableFuture example
     * 
     * No point making separate order/delivery services, just define them
     * within one service (CompletableFutureService).
     * 
     * So this example currently does nothing new VS what's in the previous examples,
     * just using descriptive names. 
     * 
     * Each service call takes 2 seconds. They should be executed sequentially, they are.
     * The calls can happen in different threads as we are using async calls. There's no benefit
     * to doing so here, as the payment should never be taken if the order processing failed.
     */
    @GetMapping("/example5")
	public String example5() throws Exception {
		// Setup / logging
		Instant start = Util.startLog("example5");

		// Logic
		CompletableFuture<String> future = CompletableFuture
            .supplyAsync(completableFutureService.processOrder)
            .thenApplyAsync(completableFutureService.processPayment)
            .thenApplyAsync(completableFutureService.processDelivery);

		String result = future.get();
		log.info("Future completed, result is: {}", result);

		// Response / logging
		Util.endLog("example5", start);

		return "Success";
	}

    
    /*
     * Practical CompletableFuture example
     * 
     * Mimic some exceptions being thrown, in this example we intentionally throw
	 * an exception from the handle() method. Which will trigger the exceptionally
	 * call.
	 * 
	 * Starting example6 in thread http-nio-8080-exec-5
	 * Starting processOrder in thread ForkJoinPool.commonPool-worker-1
	 * Future completed, result is: Exceptionally() called, exception handled.
	 * Completed example6 in thread http-nio-8080-exec-5, execution time 2s
     */
    @GetMapping("/example6")
	public String example6() throws Exception {
		// Setup / logging
		Instant start = Util.startLog("example6");

		// Logic
		CompletableFuture<String> future = CompletableFuture
            .supplyAsync(completableFutureService.processOrder)
            .thenApplyAsync(completableFutureService.processPaymentWhichFails)
            .handle(completableFutureService.handlePaymentError)
            .thenApplyAsync(completableFutureService.processDelivery)
            .exceptionally(completableFutureService.handleException);

		String result = future.get();
		log.info("Future completed, result is: {}", result);

		// Response / logging
		Util.endLog("example6", start);

		return "Success";
	}

	/*
	 * An endpoint that will load 2 very large files, and return when they all successfully load
	 */
    @GetMapping("/example7")
	public String example7() throws Exception {
		// Setup / logging
		Instant start = Util.startLog("example7");

		List<String> fileNames = new ArrayList<>();
		/*
        fileNames.add("C:\\Users\\SamSnowball\\Desktop\\Projects\\Resources\\1GBfile - first.txt");
        fileNames.add("C:\\Users\\SamSnowball\\Desktop\\Projects\\Resources\\1GBfile - second.txt");
		*/
		/*
        fileNames.add("C:\\Users\\SamSnowball\\Desktop\\Projects\\Resources\\10mb1.txt");
        fileNames.add("C:\\Users\\SamSnowball\\Desktop\\Projects\\Resources\\10mb2.txt");
		*/
		fileNames.add("C:\\Users\\SamSnowball\\Desktop\\Projects\\Resources\\100mb1.txt");
        fileNames.add("C:\\Users\\SamSnowball\\Desktop\\Projects\\Resources\\100mb2.txt");

		// Why does completable future only work on Runnable and Supplier types? I want to pass in a Function.
		// Try to de-duplicate the below code

		// Load 2 files async, and return their length
		CompletableFuture<Integer> result1 = CompletableFuture.supplyAsync(() -> {
			Instant file1Start = Util.startLog("file1");
			log.info("Starting to load in file {}", fileNames.get(0));

			String contents = null;
			try {
				contents = new String(Files.readAllBytes(Paths.get(fileNames.get(0))));
			} catch (IOException e) {
				e.printStackTrace();

			}

			Util.endLog("file1", file1Start);
			log.info("Finished loading in file {}", fileNames.get(0));

			return contents.length();
		});
		CompletableFuture<Integer> result2 = CompletableFuture.supplyAsync(() -> {
			Instant file2Start = Util.startLog("file2");
			log.info("Starting to load in file {}", fileNames.get(1));

			String contents = null;
			try {
				contents = new String(Files.readAllBytes(Paths.get(fileNames.get(1))));
			} catch (IOException e) {
				e.printStackTrace();

			}
			Util.endLog("file2", file2Start);
			log.info("Finished loading in file {}", fileNames.get(1));
			return contents.length();
		});
		
		// Then combine the futures and print the total length loaded
		CompletableFuture<Integer> result = result1.thenCombine(result2, (actualResult1, actualResult2) -> {
			return actualResult1 + actualResult2;
		});

		log.info("Both futures completed, combined result is: {}", result.get());

		// Response / logging
		Util.endLog("example7", start);

		return "Success";
	}
}