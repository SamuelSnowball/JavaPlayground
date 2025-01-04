package com.example.demo.controller;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AuthorService;

/*
 * Trying to think of scenarios where custom exceptions are useful.. it says,
 * exceptions in business logic, and to catch existing exceptions and provide a 
 * more useful cause.
 * 
 * With a chess game, if you try to make an invalid move, you could throw a InvalidMoveException
 * which would be a... RuntimeException? Ok reading further, this is instead a validation error.
 * 
 * 
 * 
 */
@RestController
public class ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    // This will override the default error page
    @RequestMapping("error123")
    public String error(){
        return "Default error message"; // NullPointerException();
    }

    // No need to ( / doesn't make sense to..?) annotate method with throws Exception, as this throws a RuntimeException
    @GetMapping("error1")
    public void error1(){
        throw new RuntimeException(); // NullPointerException();
    }

    @GetMapping("error2")
    public void error2(){
        try {
            File f = new File("whatever");
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("error3")
    public void error3() throws IOException {
        File f = new File("whatever");
        f.createNewFile();
    }

    // Don't log and throw as it leads to duplicate error messages
    // Ok, and what happens in this case, it's similar to above, we are throwing but no catch anyway, will spring handle it..?
    @GetMapping("error4")
    public void error4() {
        try {
            new Long("Abc");
        }
        catch (NumberFormatException e){
            logger.error("Caught this error: ", e);
            throw e;  
        }
    }

    class CustomCheckedException extends Exception {
        public CustomCheckedException(String message, Throwable err){
            super(message, err);
        }
    }

    class CustomUncheckedException extends RuntimeException {
        public CustomUncheckedException(String message, Throwable err){
            super(message, err);
        }
    }

    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Order")
    public class OrderNotFoundException extends RuntimeException {
        // constructor etc ...
    }
}
