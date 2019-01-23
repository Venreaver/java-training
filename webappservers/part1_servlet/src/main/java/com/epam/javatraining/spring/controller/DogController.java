package com.epam.javatraining.spring.controller;

import com.epam.javatraining.spring.exception.DogNotFoundException;
import com.epam.javatraining.spring.model.Dog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DogController {
    private static Map<String, Dog> DOGS = new ConcurrentHashMap<>();
    public static final String DOG = "/dog";
    public static final String DOG_ID = DOG + "/{id}";

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

    @GetMapping(value = DOG, produces = APPLICATION_JSON_VALUE)
    Collection<Dog> get() {
        return DOGS.values();
    }

    @GetMapping(value = DOG_ID, produces = APPLICATION_JSON_VALUE)
    ResponseEntity get(@PathVariable String id) {
        if (DOGS.containsKey(id)) {
            return ResponseEntity.ok(DOGS.get(id));
        }
        throw new DogNotFoundException(id);
    }

    @PostMapping(value = DOG, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    Dog create(@RequestBody Dog dog) {
        dog.setId(UUID.randomUUID().toString());
        DOGS.put(dog.getId(), dog);
        return dog;
    }

    @PutMapping(value = DOG_ID, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    Dog update(@RequestBody Dog dog, @PathVariable String id) {
        if (DOGS.containsKey(id)) {
            dog.setId(id);
            DOGS.put(id, dog);
            return dog;
        }
        throw new DogNotFoundException(id);
    }

    @DeleteMapping(value = DOG_ID, produces = APPLICATION_JSON_VALUE)
    ResponseEntity delete(@PathVariable String id) {
        if (DOGS.containsKey(id)) {
            DOGS.remove(id);
            return ResponseEntity.noContent().build();
        }
        throw new DogNotFoundException(id);
    }
}
