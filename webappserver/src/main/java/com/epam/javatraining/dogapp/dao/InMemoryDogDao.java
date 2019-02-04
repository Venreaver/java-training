package com.epam.javatraining.dogapp.dao;

import com.epam.javatraining.dogapp.exception.DogNotFoundException;
import com.epam.javatraining.dogapp.model.Dog;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDogDao implements DogDao {
    private static Map<String, Dog> DOGS = new ConcurrentHashMap<>();

    public void init() {
        Dog first = new Dog("1", "First", LocalDate.of(2016, 1, 10), 30, 6);
        Dog second = new Dog("2", "Second", LocalDate.of(2015, 2, 22), 50, 12);
        Dog third = new Dog("3", "Third", LocalDate.of(2017, 3, 15), 65, 20);
        Dog fourth = new Dog("4", "Fourth", LocalDate.of(2018, 4, 6), 45, 10);
        Dog fifth = new Dog("5", "Fifth", LocalDate.of(2014, 5, 17), 64, 17);
        DOGS.put(first.getId(), first);
        DOGS.put(second.getId(), second);
        DOGS.put(third.getId(), third);
        DOGS.put(fourth.getId(), fourth);
        DOGS.put(fifth.getId(), fifth);
    }

    public Dog create(Dog dog) {
        dog.setId(UUID.randomUUID().toString());
        DOGS.put(dog.getId(), dog);
        return dog;
    }

    public Collection<Dog> getAll() {
        return DOGS.values();
    }

    public Dog get(String id) {
        if (DOGS.containsKey(id)) {
            return DOGS.get(id);
        }
        throw new DogNotFoundException(id);
    }

    public Dog update(Dog dog) {
        if (DOGS.containsKey(dog.getId())) {
            DOGS.put(dog.getId(), dog);
            return dog;
        }
        throw new DogNotFoundException(dog.getId());
    }

    public void delete(String id) {
        if (!DOGS.containsKey(id)) {
            throw new DogNotFoundException(id);
        }
        DOGS.remove(id);
    }
}
