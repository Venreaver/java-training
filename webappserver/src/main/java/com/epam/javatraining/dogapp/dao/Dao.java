package com.epam.javatraining.dogapp.dao;

import java.util.Collection;
import java.util.UUID;

public interface Dao<T> {
    T create(T dog);

    Collection<T> getAll();

    T get(UUID id);

    T update(T dog);

    int delete(UUID id);
}
