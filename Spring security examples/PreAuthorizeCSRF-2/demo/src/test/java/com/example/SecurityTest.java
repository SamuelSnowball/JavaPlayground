package com.example;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.controller.MyController;
import com.example.security.MethodSecurityConfig;

@WebMvcTest(MyController.class)
@Import(MethodSecurityConfig.class) // Class which includes @Configuration @EnableWebSecurity
                                    // @EnableGlobalMethodSecurity
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    // No CSRF token required as accessing read-only endpoint
    @Test
    @WithMockUser // To get past HTTP basic authentication
    void testGet() throws Exception {
        mockMvc.perform(get("/app"))
                .andExpect(status().isOk());
    }

    // No CSRF token required as accessing read-only endpoint
    @Test
    @WithMockUser(roles = { "TEST" })
    void testGetWithRole() throws Exception {
        mockMvc.perform(get("/app/withRole"))
                .andExpect(status().isOk());
    }

    // CSRF token required as accessing a write endpoint
    @Test
    @WithMockUser(roles = { "TEST" })
    void testPost() throws Exception {
        mockMvc.perform(post("/app/withRole")
                .with(csrf())) // This is the key
                .andExpect(status().isOk());
    }

    // Test our custom auth checker
    @Test
    @WithMockUser
    void testPreauthorizeCustomCheckerSuccess() throws Exception {
        mockMvc
                .perform(get("/app/withRoleCustomAuthChecker?name=Sam"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testPreauthorizeCustomCheckerFailure() throws Exception {
        mockMvc
                .perform(get("/app/withRoleCustomAuthChecker?name=other"))
                .andExpect(status().isForbidden());
    }
}