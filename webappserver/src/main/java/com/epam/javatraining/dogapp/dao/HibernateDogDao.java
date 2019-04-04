package com.epam.javatraining.dogapp.dao;

import com.epam.javatraining.dogapp.model.Dog;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public class HibernateDogDao implements DogDao {
    private final SessionFactory sessionFactory;

    @Override
    public Dog create(Dog dog) {
        sessionFactory.getCurrentSession().save(dog);
        return dog;
    }

    @Override
    public Collection<Dog> getAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Dog").list();
    }

    @Override
    public Dog get(UUID id) {
        return sessionFactory.getCurrentSession().get(Dog.class, id);
    }

    @Override
    public Dog update(Dog dog) {
        if (get(dog.getId()) == null) {
            return null;
        }
        sessionFactory.getCurrentSession().clear();
        sessionFactory.getCurrentSession().update(dog);
        return dog;
    }

    @Override
    public int delete(UUID id) {
        Dog dog = get(id);
        if (dog != null) {
            sessionFactory.getCurrentSession().delete(dog);
            return 1;
        }
        return 0;
    }
}
