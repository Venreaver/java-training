package com.epam.javatraining.dogapp.exception;

import java.util.UUID;

public class DogNotFoundException extends RuntimeException {
    public DogNotFoundException(UUID id) {
        super(String.format("Dog with id %s does not exist", id));
    }
}
