package com.epam.javatraining.spring.controller;

import com.epam.javatraining.spring.model.Dog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class DogController {
    private static Map<String, Dog> DOGS = new ConcurrentHashMap<>();
    public static final String DOG = "/dog";
    public static final String DOG_ID = DOG + "/{id}";

    @PostConstruct
    public void init() {
        Dog first = new Dog("1", "First", 3);
        Dog second = new Dog("2", "Second", 4);
        Dog third = new Dog("3", "Third", 8);
        Dog fourth = new Dog("4", "Fourth", 5);
        Dog fifth = new Dog("5", "Fifth", 1);
        DOGS.put(first.getId(), first);
        DOGS.put(second.getId(), second);
        DOGS.put(third.getId(), third);
        DOGS.put(fourth.getId(), fourth);
        DOGS.put(fifth.getId(), fifth);
    }

    @GetMapping(value = DOG)
    Collection<Dog> get() {
        return DOGS.values();
    }

    @GetMapping(value = DOG_ID)
    ResponseEntity get(@PathVariable String id) {
        return ResponseEntity.ok(DOGS.get(id));
    }

    @PostMapping(value = DOG)
    Dog create(@RequestBody Dog dog) {
        dog.setId(UUID.randomUUID().toString());
        DOGS.put(dog.getId(), dog);
        return dog;
    }

    @PutMapping(value = DOG_ID)
    Dog update(@RequestBody Dog dog, @PathVariable String id) {
        dog.setId(id);
        DOGS.put(id, dog);
        return dog;
    }

    @DeleteMapping(value = DOG_ID)
    ResponseEntity delete(@PathVariable String id) {
        DOGS.remove(id);
        return ResponseEntity.noContent().build();
    }
}
