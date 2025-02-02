package com.example.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
  prePostEnabled = true, // Enables pre/post authorize annotations
  securedEnabled = true, // Enables @Secured annotations, basically same as below
  jsr250Enabled = true) // Enables @RolesAllowed annotations, basically same as above
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    
}