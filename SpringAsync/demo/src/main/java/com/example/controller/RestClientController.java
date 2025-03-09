package com.example.controller;

import java.time.Duration;
import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bean.MyRestClient;
import com.example.classes.Util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Demo of various rest clients
 */
@RestController
@RequestMapping("restClientController")
@Slf4j
@RequiredArgsConstructor
public class RestClientController {

    private final MyRestClient client;

	/*
	 * Demonstrating RestClient is synchronous, it will block the controller method until the outbound HTTP request completes.
	 * 
	 * Expected logs:
	 * Starting example1
	 * (logs from HTTP request)
	 * Completed example1 
	 */
	@GetMapping("/example1")
	public String example1() throws Exception {
		// Setup / logging
		Instant start = Util.startLog("example1");

		// Logic
        String result = client.getClient().get()
                .uri("https://google.com")
                .retrieve()
                .body(String.class);

        log.info(result);

		// Response / logging
		Util.endLog("example1", start);

		return "Success";
	}
}