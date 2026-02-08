package com.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
Not really testing the functionality it just executes the methods, 
it's an easy way to view the logs and see the threads that are running the different parts of the code.
*/
@SpringBootTest
class ExecutorServiceDemoTest {

    @Autowired
    private ExecutorServiceDemo executorServiceDemo;

    // Not mocking any services as I want to use them..!

    /*
    Submit a runnable to an executorService
    This is going to start the task via the new thread, but return from the method instantly as we aren't blocking
    */
    @Test
    void testExample1() throws Exception {
        executorServiceDemo.example1();
    }

    /*
    Submit a callable to an executorService
    Call a method which in turn makes a (fake thread.sleep) 10 second synchronous HTTP call somewhere,
    We expect this method to return immediately, and the outbound HTTP request to complete afterwards.

    Expected logs
    Starting example2
    Completed example2
    AsyncHttpServiceCallable call executing
    AsyncHttpServiceCallable call finished

    (roughly in order, the "Completed example2 / AsyncHttpServiceCallable call" executing - could be other way around)
    */
    @Test
    void testExample2() throws Exception {
        executorServiceDemo.example2();
    }

    /*
    Same as above method, but make the method will block until the request spawned from the
    executorService completes or times out.

    Expected logs for a timeout of 5 seconds
    Starting example3
    AsyncHttpServiceCallable call executing...
    Completed example3
    Returns: Tasks failed to execute in time

    Expected logs for a timeout of > 10 seconds
    Starting example3
    AsyncHttpServiceCallable call executing...
    AsyncHttpServiceCallable call finished.
    Completed example3
    Returns: Success
    */
    @Test
    void testExample3WithShortTimeout() throws Exception {
        String result = executorServiceDemo.example3("5");
        // This should timeout since the callable takes 10 seconds
    }

    /*
    Same test but with a longer timeout so it should succeed
    */
    @Test
    void testExample3WithLongTimeout() throws Exception {
        String result = executorServiceDemo.example3("15");
        // This should succeed since we're giving it 15 seconds
    }
}
