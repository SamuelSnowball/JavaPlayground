package com.example.service;

import org.junit.jupiter.api.Test;

/*
Not really testing the functionality it just executes the methods, 
it's an easy way to view the logs and see the threads that are running the different parts of the code.
*/
public class ThreadTest {
    
    ThreadService service = new ThreadService();

    @Test
    void testBasicThread() throws InterruptedException {
        service.basicThread();
    }

    @Test
    void testOrderedThreadJoins() throws InterruptedException {
        service.orderedThreadJoins();
    }

}
