package com.example;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

/*
If we are passed a query parameter called 'requiredParameter', then allow the request
*/
@Slf4j
public class CustomFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requiredParameter = request.getParameter("requiredParameter");

        if (requiredParameter != null && !requiredParameter.isEmpty()) {
            log.info("CustomFilter running, required parameter was found... allowing request to proceed");
            filterChain.doFilter(request, response);
            return; // Very important!
        }

        log.info("CustomFilter running, required parameter was not found! Denying request");

        // Otherwise deny the request
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
