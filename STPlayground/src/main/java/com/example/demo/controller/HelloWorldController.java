package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DummyClass;
import com.example.demo.service.MyService;

@RestController
public class HelloWorldController {

    private final MyService service;
	private final DummyClass dummyClass;

	public HelloWorldController(MyService service, DummyClass dummyClass){
		this.service = service;
		this.dummyClass = dummyClass;
	}

	@GetMapping("/helloworld")
	public String helloworld() {
		service.doLogic();
		return "Hello world!";
	}

	@GetMapping("/name")
	public String getMethodName() {
		return dummyClass.getName();
	}

	@PostMapping("/finalise")
	public void finalise() {
		return;
	}
}
