package com.epam.javatraining.spring.controller;

import com.epam.javatraining.spring.dao.InMemoryDogDao;
import com.epam.javatraining.spring.model.Dog;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/dog", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class DogController {
    private static final String DOG_ID = "/{id}";
    private final InMemoryDogDao dogDao;

    @GetMapping
    Collection<Dog> get() {
        return dogDao.getAll();
    }

    @GetMapping(value = DOG_ID, produces = APPLICATION_JSON_VALUE)
    Dog get(@PathVariable String id) {
        return dogDao.get(id);
    }

    @PostMapping
    Dog create(@Valid @RequestBody Dog dog) {
        return dogDao.create(dog);
    }

    @PutMapping(value = DOG_ID)
    Dog update(@Valid @RequestBody Dog dog, @PathVariable String id) {
        dog.setId(id);
        return dogDao.update(dog);
    }

    @DeleteMapping(value = DOG_ID)
    ResponseEntity delete(@PathVariable String id) {
        dogDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
