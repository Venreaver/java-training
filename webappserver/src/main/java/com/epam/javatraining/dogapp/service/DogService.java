package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.model.Dog;

import java.util.Collection;
import java.util.UUID;

public interface DogService {
    Collection<Dog> getAll();

    Dog get(UUID id);

    Dog create(Dog dog);

    Dog update(Dog dog);

    int delete(UUID id);
}
