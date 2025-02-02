package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class MyController {

    @GetMapping
    public ResponseEntity get() {
        // Will work with no CSRF token as this is a read only HTTP verb.
        return ResponseEntity.ok("get");
    }

    @GetMapping("/withRole")
    @PreAuthorize("hasRole('ROLE_TEST')")
    public ResponseEntity getWithRole() {
        // Will work with no CSRF token as this is a read only HTTP verb.
        return ResponseEntity.ok("get");
    }

    @PostMapping("/withRole")
    @PreAuthorize("hasRole('ROLE_TEST')")
    public ResponseEntity post() {
        // Requires a CSRF token as this is a write HTTP verb.
        return ResponseEntity.ok("post");
    }

    @GetMapping("/withRoleCustomAuthChecker")
    @PreAuthorize("@authz.decide(#root, #name)")
    public String customAuthChecker(
            @RequestParam String name) {
        return "User is authenticated!";
    }

    @Component("authz")
    class AuthorizationLogic {
        public boolean decide(MethodSecurityExpressionOperations operations, String name) {
            if(name.equals("Sam")){
                return true;
            }
            return false;
        }
    }
}
