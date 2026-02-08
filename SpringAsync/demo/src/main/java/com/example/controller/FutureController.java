package com.example.controller;

import java.util.concurrent.Future;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.MyFutureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("futureController")
@Slf4j
@RequiredArgsConstructor
public class FutureController {

	private final MyFutureService myFutureService;

	/*
	 * Basic Future example, the Future object is returned from an executorService.submit method
	 * We block the controller thread until the future completes
	 * Added a comment showing get with a timeout
	*/
	@GetMapping("/example1")
	public String example1() throws Exception {
		// Logic
        Future<String> result = myFutureService.process();
		result.get(); // Block the controller thread and wait for completeion
		// result.get(10, TimeUnit.SECONDS); // Would throw a timeout exception unless it had resolved in time

		return "Success";
	}

	/*
	* Another Future example, the Future object is returned from an executorService.submit method
	* We call Future.isDone in a loop as to not block the controller thread, however in this example we 
	* End up blocking it anyway with thread.sleep as we don't have other logic to perform
	* When it is done, we call .get() to value the result
	* We can also cancel futures...
	* If we know the return value we can return: Future<String> completableFuture = CompletableFuture.completedFuture("Hello");
	* Which means completableFuture.get() will never block.
	*/
	@GetMapping("/example2")
	public String example2() throws Exception {
		// Logic
        Future<String> result = myFutureService.process();
		while(!result.isDone()){
			log.info("Waiting for future to complete...");
			Thread.sleep(2000);
		}
		result.get(); // Block the controller thread and wait for completeion

		return "Success";
	}

}