package com.epam.javatraining.spring.exception;

public class DogNotFoundException extends RuntimeException {
    public DogNotFoundException(String id) {
        super(String.format("Dog with id %s does not exist", id));
    }
}