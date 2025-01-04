package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.generatedclasses.tables.pojos.Author;
import com.example.demo.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	// Get all authors, with some optional query parameters
	@GetMapping
	public List<Author> get(
		@RequestParam String firstName,
		@RequestParam String lastName) {
		return authorService.get(firstName, lastName);
	}

	@PostMapping
	public Author create(
		@RequestBody Map<String, String> names) {
		return authorService.insert(names.get("firstName"), names.get("lastName"));
	}

	@GetMapping("/getBooksForAuthor")
	public void getBooksForAuthor(
		@RequestParam String firstName,
		@RequestParam String lastName) {
		authorService.selectIntoMultipleObjects(firstName, lastName);
	}

}
