package com.example.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.classes.Util;
import com.example.service.AsyncServiceRunnable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("rawTheadController")
@Slf4j
@RequiredArgsConstructor
public class RawThreadController {

	// Spin off a runnable within a thread, use .join to block for completeion
	@GetMapping("/example1")
	public String example1() throws InterruptedException {
		// Setup / logging
		Instant start = Util.startLog("example1");

		// Logic
		Thread t = new Thread(new AsyncServiceRunnable());
		t.start();
		t.join();
		log.info("Completed thread {}", t.getName());

		// Response / logging
		Util.endLog("example1", start);

		return "Success";
	}

	/*
	 * Start 3 threads and wait for them all to complete.
	 * The forEach will wait for the threads to complete in order, as the threads
	 * arrayList is
	 * ordered and I'm looping over calling t.join(); So it will receieve t1 first
	 * and .join on that,
	 * which will block the main thread until it has finished.
	 * 
	 * Even if threads t2 or t3 has finished, it will wait for t1 to finish before
	 * checking the others.
	 * 
	 * The ordering of the output logs makes sense, it starts all the threads,
	 * prints 'pausing original thread' roughly before or after starting the
	 * threads,
	 * and as the threads complete it logs 'pausing original thread' again.
	 * 
	 * Sample Logs:
	 * 
	 * Starting example2 in thread http-nio-8080-exec-1
	 * Pausing original thread for t.join() <-- This happens towards the start
	 * AsyncServiceRunnable service method running in thread Thread-9
	 * AsyncServiceRunnable service method running in thread Thread-8
	 * AsyncServiceRunnable service method running in thread Thread-7
	 * Finished AsyncServiceRunnable service running in thread Thread-9
	 * Finished AsyncServiceRunnable service running in thread Thread-7
	 * Finished AsyncServiceRunnable service running in thread Thread-8
	 * Completed thread Thread-7 <-- First thread completes
	 * Pausing original thread for t.join() <-- Before we can join on the next one
	 * Completed thread Thread-8
	 * Pausing original thread for t.join()
	 * Completed thread Thread-9
	 * All threads completed!
	 * Completed example2 in thread http-nio-8080-exec-1, execution time 10s
	 */
	@GetMapping("/example2")
	public String example2() throws InterruptedException {
		// Setup / logging
		Instant start = Util.startLog("example2");

		// Logic
		List<Thread> threads = new ArrayList<>();
		Thread t1 = new Thread(new AsyncServiceRunnable());
		Thread t2 = new Thread(new AsyncServiceRunnable());
		Thread t3 = new Thread(new AsyncServiceRunnable());
		threads.add(t1);
		threads.add(t2);
		threads.add(t3);

		threads.stream().forEach(t -> t.start());
		threads.stream().forEach(t -> {
			log.info("Pausing original thread for t.join()");
			try {
				t.join();
			} catch (InterruptedException e) {
				log.info("Thread {} interrupted!", t.getName());
			}
			log.info("Completed thread {}", t.getName());
		});
		log.info("All threads completed!");

		// Response / logging
		Util.endLog("example2", start);

		return "Success";
	}
}