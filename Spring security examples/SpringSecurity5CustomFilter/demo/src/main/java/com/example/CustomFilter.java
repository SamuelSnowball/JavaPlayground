package com.example;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFilter extends GenericFilterBean {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        
        String requiredParameter = request.getParameter("requiredParameter");
        
        log.info("Recieved requiredParameter was: {}", requiredParameter);

        // If we are passed a query parameter called 'requiredParameter', then allow the request
        if (requiredParameter != null && requiredParameter.length() > 0) {
            chain.doFilter(request, response);
        }

        // Otherwise deny the request
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        return;
    }
}
