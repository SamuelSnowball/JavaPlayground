package com.example.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class BatchInserterServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private ItemService itemService;

    @Autowired
    private BatchInserterService batchInserterService;

    @BeforeEach
    void setUp() {
        itemService.clearDB();
    }

    // -------------------------------------------------------------------------
    // batchInsert – concurrent threads
    // -------------------------------------------------------------------------

    @Test
    void batchInsert_twoThreadsConcurrently_insertsAllItems() throws InterruptedException {
        // Try with fork join, and own pool
        // Check how many DB threads we have, and how many we can use in parallel
        // without exhausting the pool

        Thread t1 = new Thread(batchInserterService);
        Thread t2 = new Thread(batchInserterService);
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        int size = itemService.getItemTableSize();
        assertThat(size).isEqualTo(20000);
    }

}
