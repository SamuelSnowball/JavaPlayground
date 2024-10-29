package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jooq")
public class JOOQController {

    @GetMapping("/test")
	public String testjooq() {
		
		return "Hello world!";
	}
}
