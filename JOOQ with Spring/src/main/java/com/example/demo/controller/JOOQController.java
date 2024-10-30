package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.JOOQExample;

@RestController
@RequestMapping("/jooq")
public class JOOQController {

	@Autowired
	private JOOQExample jooq;

    @GetMapping("/getFirstNames")
	public List<String> testjooq() {
		return jooq.getFirstNames();
	}
}
