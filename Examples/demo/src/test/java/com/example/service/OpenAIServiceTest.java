package com.example.service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.openai.client.OpenAIClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class OpenAIServiceTest {

    @Mock
    private OpenAIClient client;

    @InjectMocks
    private OpenAIService service;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    private static final List<String> ITEMS = List.of(
        "Apple",
        "Carrot",
        "Chicken Breast",
        "Almonds",
        "Whole Wheat Bread",
        "Cheddar Cheese",
        "Salmon Fillet",
        "Black Beans",
        "Olive Oil",
        "Chocolate Cake"
    );

    @Test
    void testCategorize() {
        String response = service.categorize(ITEMS);
    }


    @Test
    void testCategorizeMultiple() throws InterruptedException {

        // list of runnables?
        // submit same thing 10 times?
        // for loop?

        List<Callable<String>> tasks = IntStream.range(0, 10)
            .mapToObj(i -> (Callable<String>) () -> {
                String response = service.categorize(ITEMS);
                log.info("Received response: {}, on thread: {}", response, Thread.currentThread().getName());
                return response;
            })
            .collect(Collectors.toList());

        List<Future<String>> results = executor.invokeAll(tasks);

        expect(client).toHaveBeenCalledTimes(1);

        // Expect client to have been called once, as should return cached result for all subsequent calls

        results.forEach(future -> {
            try {
                String response = future.get();
                log.info("Final response: {}", response);
            } catch (Exception e) {
                log.error("Error getting response", e);
            }
        });

    } 
    
}
