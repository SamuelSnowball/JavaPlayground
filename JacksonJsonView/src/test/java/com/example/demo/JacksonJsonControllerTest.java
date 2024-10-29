package com.example.demo;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.JacksonJsonController;

@WebMvcTest(JacksonJsonController.class)
public class JacksonJsonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testCreateUser() throws Exception {
        this.mvc.perform(get("/jackonjson/createUser"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name", is("User 1")))
      .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    void testCreateUserWithSpringIntegration() throws Exception {
        this.mvc.perform(get("/jackonjson/createUserWithSpringIntegration"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name", is("User 1")))
      .andExpect(jsonPath("$.id").doesNotExist());
    }
}
