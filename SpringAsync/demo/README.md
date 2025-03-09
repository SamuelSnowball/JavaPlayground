Contains several different controllers demoing async concepts / examples:

- RawThreadController
    - The simplest example, creating raw Threads and running them with t.start(), and blocking using t.join()

- FutureController
    - Using Java 5 Futures

- ExecutorServiceController
    - Using Java 8 executorService for less thread boilerplate

- CompletableFutureController
    - Using Java 8 CompletableFuture for less thread boilerplate / management.

- RestClientController
    - Uses various rest clients to make external calls.


To do
- Add callable examples to RawThreadController



Make an endpoint which calls out to 2 other methods async / on different threads, and when they complete return a response to the client
Add error handling and tests so when 1 of the requests fails, there's a partial response returned

The below code will wait for Thread t to finish before logging "Completed". However if we omit the t.join() call, then this method will run all the way through, even though the AsyncServiceRunnable task is still executing. Then, at some point the Thread t will finish executing.
```
Thread t = new Thread(new AsyncServiceRunnable());
t.start();
t.join();

log.info("Completed");
```

