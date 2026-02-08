package com.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
Not really testing the functionality it just executes the methods, 
it's an easy way to view the logs and see the threads that are running the different parts of the code.
*/
@SpringBootTest
class CompletableFutureTest {

    @Autowired
    private CompletableFutureService completableFutureService;

    // Not mocking exampleService as I want to use it..!

    /*
    14:14:01  INFO  - supplyAsyncDemo endpoint executed on thread: main
    14:14:01  INFO  - Async task has completed, result is: supplier executed on thread: ForkJoinPool.commonPool-worker-1
    */
    @Test
    void testSupplyAsyncDemo() throws Exception {
        completableFutureService.supplyAsyncDemo();
    }

    /*
    Run 1 example logs:
    11:33:37  INFO  - synchronousContinuationMethods endpoint executed on thread: main
    11:33:37  INFO  - Task told to run async, executed on thread: ForkJoinPool.commonPool-worker-1
    11:33:37  INFO  - Task told to run sync, executed on thread: ForkJoinPool.commonPool-worker-1

    Run 2 example logs:
    11:34:09  INFO  - synchronousContinuationMethods endpoint executed on thread: main
    11:34:09  INFO  - Task told to run async, executed on thread: ForkJoinPool.commonPool-worker-1
    11:34:09  INFO  - Task told to run sync, executed on thread: main
    */
    @Test
    void testSynchronousContinuationMethods() throws Exception {
        completableFutureService.synchronousContinuationMethods();
    }

    /*
    See how the async task runs on an exectuor thread

    11:39:37  INFO  - asynchronousContinuationMethods endpoint executed on thread: main
    11:39:37  INFO  - Task told to run async, executed on thread: ForkJoinPool.commonPool-worker-1
    11:39:37  INFO  - Task told to run sync, executed on thread: ForkJoinPool.commonPool-worker-1
    */
    @Test
    void testAsynchronousContinuationMethods() throws Exception {
        completableFutureService.asynchronousContinuationMethods();
    }

    /*
    Each task has to wait for the previous one to complete before it can start. ~15 seconds total.

    14:03:28  INFO  - sequentialAsyncTasksExample endpoint executed on thread: main
    14:03:28  INFO  - Starting processOrder in thread ForkJoinPool.commonPool-worker-1
    14:03:28  INFO  - sequentialAsyncTasksExample function continuing doing stuff.. executed on thread: main
    14:03:33  INFO  - Starting processPayment in thread ForkJoinPool.commonPool-worker-1
    14:03:38  INFO  - Starting processDelivery in thread ForkJoinPool.commonPool-worker-1
    14:03:43  INFO  - All async tasks have completed, final result is: Delivery processed

    So remember that it will use an executor thread for the async tasks, 
    but the main thread will continue doing other stuff until it needs to wait for the result of the async task.
    */
    @Test
    void testSequentialAsyncTasksExample() throws Exception {
        completableFutureService.sequentialAsyncTasksExample();
    }

    /*
    Even though there are 3 tasks and each task takes 5 seconds, it should complete in ~5 seconds as the tasks are 
    executed in parallel.

    14:10:53  INFO  - parallelAsyncTasksExample endpoint executed on thread: main
    14:10:53  INFO  - parallelAsyncTasksExample function continuing doing stuff.. executed on thread: main
    14:10:53  INFO  - Starting getRecommendations in thread ForkJoinPool.commonPool-worker-1
    14:10:53  INFO  - Starting getTrendingItems in thread ForkJoinPool.commonPool-worker-2
    14:10:53  INFO  - Starting getLatestDeals in thread ForkJoinPool.commonPool-worker-3
    14:10:58  INFO  - Recommendations result: Some recommendations...
    14:10:58  INFO  - Trending items result: Some trending items...
    14:10:58  INFO  - Latest deals result: Some latest deals...
    14:10:58  INFO  - All async tasks have completed!
    */
    @Test
    void testParallelAsyncTasksExample() throws Exception {
        completableFutureService.parallelAsyncTasksExample();
    }

    /*
    14:46:55  INFO  - asyncExceptionExample endpoint executed on thread: main
    14:46:55  INFO  - Starting processOrder in thread ForkJoinPool.commonPool-worker-1
    14:47:00  INFO  - Handling error, result: null, exception: java.util.concurrent.CompletionException: java.lang.RuntimeException: Payment failed
    14:47:00  INFO  - Exceptionally() called, exception handled.
    14:47:00  INFO  - Final result is: Default value

    So result is null here as the method threw an exception.
    */
    @Test
    void testAsyncExceptionExample() throws Exception {
        completableFutureService.asyncExceptionExample();
    }
}
