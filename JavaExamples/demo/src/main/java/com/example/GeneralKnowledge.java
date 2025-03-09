package com.example;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeneralKnowledge {
    public static void main(String[] args) {
        
        int[] numbers = {0, 1, 2};
        // Arrays.asList(numbers); // This creates a List of int[], so imagine, List {[0,1], [1,2], [2,3]}
        Arrays.asList(numbers).stream().forEach(n -> log.info("{}", n)); // This logs [0,1,2] as a single element in the list
    }
}
