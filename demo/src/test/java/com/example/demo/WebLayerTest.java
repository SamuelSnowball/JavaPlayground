package com.example.demo;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.HelloWorldController;
import com.example.demo.service.MyService;

@WebMvcTest(HelloWorldController.class)
@AutoConfigureMockMvc
public class WebLayerTest {
    
    @Autowired
	private MockMvc mockMvc;

    @MockBean
    private MyService service;

	@Test
	void greetingShouldReturnDefaultMessage() throws Exception {
        when(service.doLogic()).thenReturn("Hello, Mock");
		this.mockMvc.perform(get("/helloworld")).andExpect(content().string(containsString("Hello world!")));
	}
}
