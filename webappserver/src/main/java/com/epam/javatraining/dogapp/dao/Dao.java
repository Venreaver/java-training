package com.epam.javatraining.dogapp.dao;

import java.util.Collection;

public interface Dao<T> {
    T create(T dog);

    Collection<T> getAll();

    T get(String id);

    T update(T dog);

    int delete(String id);
}
