package com.example;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MyController.class)
public class MyControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUnsecuredEndpoint() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSecuredEndpoint() throws Exception {
        mockMvc.perform(get("/secured"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSecuredPostEndpoint() throws Exception {
        mockMvc.perform(post("/secured").with(csrf()))
            .andExpect(status().isOk());
    }
}