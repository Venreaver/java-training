package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.dao.DogDao;
import com.epam.javatraining.dogapp.dao.JdbcConnectionHolder;
import com.epam.javatraining.dogapp.model.Dog;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class DogService {
    private final DogDao dogDao;
    private final JdbcConnectionHolder connectionHolder;

    public Collection<Dog> getAll() {
        return execute(dogDao::getAll);
    }

    public Dog get(String id) {
        return execute(() -> dogDao.get(id));
    }

    public Dog create(Dog dog) {
        return execute(() -> dogDao.create(dog));
    }

    public Dog update(Dog dog) {
        return execute(() -> dogDao.update(dog));
    }

    public void delete(String id) {
        execute(() -> dogDao.delete(id));
    }

    private <T> T execute(final Supplier<T> supplier) {
        try {
            Connection connection = connectionHolder.getConnection();
            connection.setAutoCommit(false);
            try {
                T result = supplier.get();
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionHolder.closeConnection();
        }
    }
}