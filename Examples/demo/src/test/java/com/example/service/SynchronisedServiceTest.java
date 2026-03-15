package com.example.service;

import org.junit.jupiter.api.Test;

public class SynchronisedServiceTest {
    private SynchronisedService synchronisedService = new SynchronisedService();

    @Test
    void synchronisedBehaviour() {
        /*
         * Whatever thread executes here will aquire the lock on the instance, however in this case we are executing the methods on the same thread so the lock isn't being
         * contested here.
         * 
         * So this will just wait for all methods to complete.
         */
        synchronisedService.synchronisedMethodA();
        synchronisedService.synchronisedMethodB();
        synchronisedService.nonSynchronisedMethod();
    }

    /*
    This will scheduled all 3 threads to be started, but remember
    they might not be executed in the order you defined.

    So any of them could run first, and can change between runs.

    So essentially, we will see one of the threads get
    scheduled first and see the logs from the relevant method.

    Then the other 2 threads will, eventually start.
    Thing is that one of the threads doing the synchonrised work 
    will lock the class instance, so the other thread will 
    get blocked for 5 seconds until the lock is released.

    Example logs 1:
    21:55:24.783 [Thread-2] INFO com.example.service.SynchronisedService -- Executing non-synchronised method
    21:55:24.783 [Thread-0] INFO com.example.service.SynchronisedService -- Executing synchronised method A
    21:55:29.794 [Thread-1] INFO com.example.service.SynchronisedService -- Executing synchronised method B
    
    Example logs 2:
    21:56:52.636 [Thread-1] INFO com.example.service.SynchronisedService -- Executing synchronised method B
    21:56:52.636 [Thread-2] INFO com.example.service.SynchronisedService -- Executing non-synchronised method
    21:56:57.645 [Thread-0] INFO com.example.service.SynchronisedService -- Executing synchronised method A
    */
    @Test
    void synchronisedBehaviourWithMultipleThreads() {

        Runnable task1 = () -> {
            synchronisedService.synchronisedMethodA();
        };

        Runnable task2 = () -> {
            synchronisedService.synchronisedMethodB();
        };

        Runnable task3 = () -> {
            synchronisedService.nonSynchronisedMethod();
        };

        // Will use the default fork join pool
        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        Thread t3 = new Thread(task3);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
