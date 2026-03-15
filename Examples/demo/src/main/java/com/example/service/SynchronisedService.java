package com.example.service;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SynchronisedService {
    
    public synchronized void synchronisedMethodA() {
        log.info("Executing synchronised method A");
        try {
            Thread.sleep(5000); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void synchronisedMethodB() {
        log.info("Executing synchronised method B");
        try {
            Thread.sleep(5000); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void nonSynchronisedMethod() {
        log.info("Executing non-synchronised method");
        try {
            Thread.sleep(5000); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
