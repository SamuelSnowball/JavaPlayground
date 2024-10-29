package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DummyClass;
import com.example.demo.service.MyService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HelloWorldController {

    @Autowired
    private MyService service;

	@Autowired
	private DummyClass dummyClass;

	@GetMapping("/helloworld")
	public String helloworld() {
		service.doLogic();
		return "Hello world!";
	}

	@GetMapping("/name")
	public String getMethodName() {
		return dummyClass.getName();
	}
}
