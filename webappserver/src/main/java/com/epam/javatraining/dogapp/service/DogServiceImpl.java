package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.dao.DogDao;
import com.epam.javatraining.dogapp.model.Dog;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

@Transactional
@NoArgsConstructor
@AllArgsConstructor
public class DogServiceImpl implements DogService {
    private DogDao dogDao;

    public Collection<Dog> getAll() {
        return dogDao.getAll();
    }

    public Dog get(UUID id) {
        return dogDao.get(id);
    }

    public Dog create(Dog dog) {
        return dogDao.create(dog);
    }

    public Dog update(Dog dog) {
        return dogDao.update(dog);
    }

    public int delete(UUID id) {
        return dogDao.delete(id);
    }
}