package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.dao.JdbcConnectionHolder;
import com.epam.javatraining.dogapp.model.Dog;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class TransactionalDogService implements DogService {
    private final DogService dogServiceImpl;
    private final JdbcConnectionHolder connectionHolder;

    public Collection<Dog> getAll() {
        return execute(dogServiceImpl::getAll);
    }

    public Dog get(String id) {
        return execute(() -> dogServiceImpl.get(id));
    }

    public Dog create(Dog dog) {
        return execute(() -> dogServiceImpl.create(dog));
    }

    public Dog update(Dog dog) {
        return execute(() -> dogServiceImpl.update(dog));
    }

    public int delete(String id) {
        return execute(() -> dogServiceImpl.delete(id));
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
