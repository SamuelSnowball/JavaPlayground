package com.example.service;

public class VolatileService implements Runnable {
    
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            // Do some work
        }
    }

    public void stop() {
        running = false;
    }
}
