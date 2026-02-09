package com.example.service;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/*
Not really testing the functionality it just executes the methods, 
it's an easy way to view the logs and see the threads that are running the different parts of the code.
*/
@Slf4j
class RestClientServiceTest {
    
    private RestClientService restClientService = new RestClientService();

    /*
    The API call will take 5 seconds, so as it's synchronous, we expect the test to take 5 seconds to complete.

    17:26:17.278 [main] INFO com.example.service.RestClientServiceTest -- Starting test on thread: main
    17:26:17.280 [main] INFO com.example.service.RestClientService -- Starting callGoogleWrapper on thread: main
    17:26:17.280 [main] INFO com.example.service.RestClientService -- Starting callGoogle on thread: main
    17:26:23.582 [main] INFO com.example.service.RestClientService -- Finished executing callGoogleWrapper on thread: main
    17:26:23.582 [main] INFO com.example.service.RestClientServiceTest -- Finished test on thread: main
    */
    @Test
    void testCallGoogle() throws Exception {
        log.info("Starting test on thread: " + Thread.currentThread().getName());
        restClientService.callGoogle();
        log.info("Finished test on thread: " + Thread.currentThread().getName());
    }

}
