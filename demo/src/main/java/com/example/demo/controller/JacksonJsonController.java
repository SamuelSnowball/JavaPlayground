package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/jackonjson")
public class JacksonJsonController {
    private static final Logger logger = LoggerFactory.getLogger(JacksonJsonController.class);

	// Create a user, and return a view to only return their name
	// What if I instead left out the ID in the user toString method? well, using toString wouldn't return JSON
	@GetMapping("/createUser")
	public String createUser() {
		User newUser = new User(0, "User 1");

		// Now we want to serialize that user object and return it to the client
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

		String result = "JsonProcessingException..."; // This should be valid JSON
		try {
			result = mapper
				.writerWithView(Views.Public.class)
				.writeValueAsString(newUser);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return result;
		}

		return result;
	}

	// This is the Spring Integration with @JsonView
	// Note we are using @JsonView on the method itself, it has the has the same behaviour as above
	@JsonView(Views.Public.class)
	@GetMapping("/createUserWithSpringIntegration")
	public User createUserSpringIntegration() {
		User newUser = new User(0, "User 1");
		return newUser;
	}

	interface Views {
		public static class Public {

		}
	}

	class User {
		int id;

		@JsonView(Views.Public.class)
		String name;
		public User(int id, String name) {
			this.id = id;
			this.name = name;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]";
		}
	}
}
