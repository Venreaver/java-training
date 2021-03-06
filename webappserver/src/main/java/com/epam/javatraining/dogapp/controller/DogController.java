package com.epam.javatraining.dogapp.controller;

import com.epam.javatraining.dogapp.aspectj.Log;
import com.epam.javatraining.dogapp.exception.DogNotFoundException;
import com.epam.javatraining.dogapp.model.Dog;
import com.epam.javatraining.dogapp.service.DogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(path = "dog")
public class DogController {
    private static final String DOG_ID = "{id}";
    private final DogService dogService;

    @GetMapping
    Collection<Dog> get() {
        return dogService.getAll();
    }

    @Log
    @GetMapping(path = DOG_ID)
    Dog get(@PathVariable UUID id) {
        Dog dog = dogService.get(id);
        if (dog == null) {
            throw new DogNotFoundException(id);
        }
        return dog;
    }

    @PostMapping
    Dog create(@Valid @RequestBody Dog dog) {
        return dogService.create(dog);
    }

    @PutMapping(path = DOG_ID)
    Dog update(@Valid @RequestBody Dog dog, @PathVariable UUID id) {
        dog.setId(id);
        Dog updatedDog = dogService.update(dog);
        if (updatedDog == null) {
            throw new DogNotFoundException(id);
        }
        return updatedDog;
    }

    @DeleteMapping(path = DOG_ID)
    ResponseEntity delete(@PathVariable UUID id) {
        int rowCount = dogService.delete(id);
        if (rowCount < 1) {
            throw new DogNotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }
}
