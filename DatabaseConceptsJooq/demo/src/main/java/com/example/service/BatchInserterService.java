package com.example.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.service.ItemService.ItemInput;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public  class BatchInserterService implements Runnable {

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