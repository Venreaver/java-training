package com.epam.javatraining.dogapp.dao;

import com.epam.javatraining.dogapp.model.Dog;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.util.Collection;

@AllArgsConstructor
public class JdbcDogDao implements DogDao {
    private final DataSource dataSource;

    @Override
    public Dog create(Dog dog) {
        return null;
    }

    @Override
    public Collection<Dog> getAll() {
        return null;
    }

    @Override
    public Dog get(String id) {
        return null;
    }

    @Override
    public Dog update(Dog dog) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
