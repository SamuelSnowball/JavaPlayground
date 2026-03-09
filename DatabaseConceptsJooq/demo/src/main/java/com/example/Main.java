package com.example;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.service.ItemService;
import com.example.service.ItemService.ItemInput;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /**
     * Demo runner: performs a batch insert of sample items on startup.
     */
    @Bean
    CommandLineRunner demo(ItemService itemService, BatchInserter inserter) {
        // Don't run any code here - as the application context is not yet ready!
        
        return args -> {
            // Clear the DB before inserting new data
            itemService.clearDB();

            itemService.logItemTableSize();

            // Try with fork join, and own pool
            // Check how many DB threads we have, and how many we can use in parallel
            // without exhausting the pool

            Thread t1 = new Thread(inserter);
            Thread t2 = new Thread(inserter);
            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            itemService.logItemTableSize();
        };
    }

    @Component
    @RequiredArgsConstructor
    @Slf4j
    static class BatchInserter implements Runnable {

        private final ItemService itemService;

        @Override
        public void run() {
            log.info("Starting batch insert in thread {}", Thread.currentThread().getName());

            List<ItemInput> items = itemService.generateInputData(10000);

            // Call batchInsert externally so Spring can create the transaction proxy around
            // it
            itemService.batchInsert(items);
            log.info("Finished batch insert in thread {}", Thread.currentThread().getName());
        }
    }
}
