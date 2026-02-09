package com.example;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

/*
If we are passed a query parameter called 'requiredParameter', then allow the request
*/
@Slf4j
public class CustomFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        
        String requiredParameter = request.getParameter("requiredParameter");

        if (requiredParameter != null && !requiredParameter.isEmpty()) {
            log.info("CustomFilter running, required parameter was found... allowing request to proceed");
            chain.doFilter(request, response);
            return; // Important!
        }

        log.info("CustomFilter running, required parameter was not found! Denying request");

        // Otherwise deny the request
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
