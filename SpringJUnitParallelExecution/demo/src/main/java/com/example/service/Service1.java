package com.example.service;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Service1 {
    
    public void method1(){
        String threadName = Thread.currentThread().getName();
        log.info("Starting service1 method1() in thread {}", threadName);

        try {
            Thread.sleep(10000);
        }
        catch(InterruptedException e){
            log.error("Thread {} interrupted!", threadName);
        }

        log.info("Finished service1 method1() in thread {}", threadName);
    }

    public void method2(){
        String threadName = Thread.currentThread().getName();
        log.info("Starting service1 method2() in thread {}", threadName);

        try {
            Thread.sleep(10000);
        }
        catch(InterruptedException e){
            log.error("Thread {} interrupted!", threadName);
        }

        log.info("Finished service1 method2() in thread {}", threadName);
    }
}
