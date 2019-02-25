package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.model.Dog;

import java.util.Collection;

public interface DogService {
    Collection<Dog> getAll();

    Dog get(String id);

    Dog create(Dog dog);

    Dog update(Dog dog);

    int delete(String id);
}
