package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;

/*
 * Shows how we can handle exceptions within lambdas directly, or move the exception handling logic into another method to keep the lambda concise.
 */
@Slf4j
public class ExceptionsInLambdas {
    public static void main(String[] args) {

        List<String> fileNames = new ArrayList<>();
        fileNames.add("C:\\Users\\SamSnowball\\Desktop\\Projects\\JavaPlayground\\JavaExamples\\demo\\src\\main\\resources\\file1.txt");
        fileNames.add("C:\\Users\\SamSnowball\\Desktop\\Projects\\JavaPlayground\\JavaExamples\\demo\\src\\main\\resources\\file2.txt");
        fileNames.add("C:\\Users\\SamSnowball\\Desktop\\Projects\\JavaPlayground\\JavaExamples\\demo\\src\\main\\resources\\file3.txt");

        // Option 1, handle the exception in the method
        fileNames.stream().forEach(filename -> {
            String contents = getFileContentsAndHandleException(filename);
            log.info("Loaded file {} with contents {}", filename, contents);
        });

        // Option 2, handle the exception in the lambda, causing bloat
        fileNames.stream().forEach(filename -> {
            String contents = null;
            try {
                contents = getFileContents(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("Loaded file {} with contents {}", filename, contents);
        });

        // Same as above, just defining our own consumer, it doesn't really do anything, just showing how to
        // define a lambda and use it. We take in a String with Consumer<String> and the consumer obviously returns void.
        Consumer<String> myConsumer = (filename) -> {
            String contents = getFileContentsAndHandleException(filename);
            log.info("Loaded file {} with contents {}", filename, contents);
        };
        fileNames.stream().forEach(myConsumer);

        // We are just moving where the exception handling logic is defined, VS handling
        // it within the lambda. 
    }

    // Handle the exception in the method so the lambda is concise
    public static String getFileContentsAndHandleException(final String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Throw the exception into the lambda so we have to handle it there
    public static String getFileContents(final String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

}