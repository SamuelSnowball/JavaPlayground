
package com.example.service;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AsyncServiceRunnable implements Runnable {

    @Override
    public void run(){
		String threadName = Thread.currentThread().getName();
		log.info("AsyncServiceRunnable service method running in thread {}", threadName);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		log.info("Finished AsyncServiceRunnable service running in thread {}", threadName);
    }

}