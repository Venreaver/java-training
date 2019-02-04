package com.epam.javatraining.dogapp.controller;

import com.epam.javatraining.dogapp.dao.DogDao;
import com.epam.javatraining.dogapp.model.Dog;
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

@RestController
@AllArgsConstructor
@RequestMapping(path = "dog")
public class DogController {
    private static final String DOG_ID = "{id}";
    private final DogDao dogDao;

    @GetMapping
    Collection<Dog> get() {
        return dogDao.getAll();
    }

    @GetMapping(path = DOG_ID)
    Dog get(@PathVariable String id) {
        return dogDao.get(id);
    }

    @PostMapping
    Dog create(@Valid @RequestBody Dog dog) {
        return dogDao.create(dog);
    }

    @PutMapping(path = DOG_ID)
    Dog update(@Valid @RequestBody Dog dog, @PathVariable String id) {
        dog.setId(id);
        return dogDao.update(dog);
    }

    @DeleteMapping(path = DOG_ID)
    ResponseEntity delete(@PathVariable String id) {
        dogDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
