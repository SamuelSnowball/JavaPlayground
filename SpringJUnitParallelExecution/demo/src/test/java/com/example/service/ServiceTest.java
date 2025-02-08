package com.example.service;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ServiceTest {
    
    @Autowired
    private Service1 service1;

    @Autowired
    private Service2 service2;

    private static Instant start;
    private static Instant end;

    public ServiceTest(){
        log.info("ServiceTest instance created..");
    }

    @BeforeAll
    static void beforeAll(){
        log.info("Starting tests..");
        start = Instant.now();
    }    

    @AfterAll
    static void afterAll(){
        end = Instant.now();
        log.info("Finished all tests! Execution time {}s.", Duration.between(start, end).toSeconds());
    }

    // With sequential execution it should take longer..

    @Test
    void testService1Method1(){
        service1.method1();
    }

    @Test
    void testService1Method2(){
        service1.method2();
    }

    /*
    @Test
    void testService2Method1(){
        service2.method1();
    }

    @Test
    void testService2Method2(){
        service2.method2();
    }
    */
}
