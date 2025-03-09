package com.example.classes;

import java.time.Duration;
import java.time.Instant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {
    public static Instant startLog(String taskName) {
        log.info("Starting {}", taskName);
        return Instant.now();
    }

    public static void endLog(String taskName, Instant start) {
        Instant end = Instant.now();
        long duration = Duration.between(start, end).toSeconds();
        log.info("Completed {}, execution time {}s", taskName,  duration);
    }
}
