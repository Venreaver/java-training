package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.dao.DogDao;
import com.epam.javatraining.dogapp.model.Dog;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
public class DogServiceImpl implements DogService {
    private DogDao dogDao;

    @Transactional
    public Collection<Dog> getAll() {
        return dogDao.getAll();
    }

    @Transactional
    public Dog get(String id) {
        return dogDao.get(id);
    }

    @Transactional
    public Dog create(Dog dog) {
        return dogDao.create(dog);
    }

    @Transactional
    public Dog update(Dog dog) {
        return dogDao.update(dog);
    }

    @Transactional
    public int delete(String id) {
        return dogDao.delete(id);
    }
}