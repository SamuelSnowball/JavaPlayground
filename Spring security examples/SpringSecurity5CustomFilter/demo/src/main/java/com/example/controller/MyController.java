package com.example.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    // Unsecured route as configured within SecurityConfig
    @GetMapping
    public ResponseEntity get() {
        // Will work with no CSRF token as this is a read only HTTP verb.
        return ResponseEntity.ok("Unsecured / route");
    }

    // All other routes secured
    // All other routes secured
    // All other routes secured

    @GetMapping("/secured")
    public ResponseEntity getSecured(@RequestParam("requiredParameter") String requiredParameter) {
        // Will work with no CSRF token as this is a read only HTTP verb.
        return ResponseEntity.ok("Secured route");
    }

    // POST requires a CSRF token
    @PostMapping("/secured")
    public ResponseEntity postSecured(@RequestParam("requiredParameter") String requiredParameter) {
        return ResponseEntity.ok("Secured route");
    }
}
