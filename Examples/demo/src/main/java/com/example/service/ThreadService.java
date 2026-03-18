package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadService {

    Runnable myRunnable = () -> {
        log.info("Thread {} is running", Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    // Spin off a runnable within a thread, use .join to block for completeion
    public void basicThread() throws InterruptedException {

        // Logic
        Thread t = new Thread(myRunnable);
        t.start();
        t.join();
        log.info("Completed thread {}", t.getName());
    }

    /*
     * Start 3 threads and wait for them all to complete.
     * The forEach will wait for the threads to complete in order, as the threads
     * arrayList is ordered and I'm looping over calling t.join(); 
     * 
     * So it will receieve t1 first and .join on that,
     * which will block the main thread until it has finished.
     * 
     * Even if threads t2 or t3 has finished, it will wait for t1 to finish before
     * checking the others.
     */
    public void orderedThreadJoins() throws InterruptedException {

        // Logic
        List<Thread> threads = new ArrayList<>();
        Thread t1 = new Thread(myRunnable);
        Thread t2 = new Thread(myRunnable);
        Thread t3 = new Thread(myRunnable);
        threads.add(t1);
        threads.add(t2);
        threads.add(t3);

        threads.stream().forEach(t -> t.start());

        // AtomicInteger here actually isn't required, as it's being incremented 
        // in a single thread, but would be required if sharing state across threads.
        AtomicInteger counter = new AtomicInteger(1); // Thread safe counter

        threads.stream().forEach(t -> {
            int i = counter.getAndIncrement();
            log.info("Pausing thread {} for t.join()", i);

            try {
                t.join();
            } catch (InterruptedException e) {
                log.info("Thread {} interrupted!", i);
            }

            log.info("Completed thread {}", i);
        });

        log.info("All threads completed!");
    }

}
