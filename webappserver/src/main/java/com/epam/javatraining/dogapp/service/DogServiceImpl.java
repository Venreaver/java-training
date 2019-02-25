package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.dao.DogDao;
import com.epam.javatraining.dogapp.model.Dog;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class DogServiceImpl implements DogService {
    private final DogDao dogDao;

    public Collection<Dog> getAll() {
        return dogDao.getAll();
    }

    public Dog get(String id) {
        return dogDao.get(id);
    }

    public Dog create(Dog dog) {
        return dogDao.create(dog);
    }

    public Dog update(Dog dog) {
        return dogDao.update(dog);
    }

    public int delete(String id) {
        return dogDao.delete(id);
    }
}