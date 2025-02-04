package com.example;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.controller.MyController;

@WebMvcTest(controllers = MyController.class)
@ContextConfiguration(classes = {CustomFilter.class})
@Import(MyController.class)
public class MyControllerTest {
    
    private MockMvc mockMvc;

    @Autowired
    private CustomFilter filter;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                             .addFilter(filter).build();
    }

    // No MockUser required as the endpoint is configured to be open
    @Test
    void testCustomFilterGetUnsecured() throws Exception {
        mockMvc.perform(get("/")
            .param("requiredParameter", "true")) // still need this to get past custom filter
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser // to get past HTTP basic authentication
    void testCustomFilterGetSecured() throws Exception {
        mockMvc.perform(get("/secured")
            .param("requiredParameter", "true")) // get past custom filter
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser // to get past HTTP basic authentication
    void testCustomFilterPost() throws Exception {
        mockMvc.perform(post("/secured")
            .param("requiredParameter", "true") // get past custom filter
            .with(csrf())) // required as endpoint uses write HTTP verb
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser // to get past HTTP basic authentication
    void testCustomFilterPostIsDenied() throws Exception {
        mockMvc.perform(post("/secured")
            // missing required request parameter
            .with(csrf())) // required as endpoint uses write HTTP verb
            .andExpect(status().isForbidden());
    }
}
