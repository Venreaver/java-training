package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.dao.JdbcConnectionHolder;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

public class CglibTransactionalDogService extends TransactionalService implements InvocationHandler {
    private CglibTransactionalDogService(JdbcConnectionHolder holder, Object target) {
        super(holder, target);
    }

    public static Object getInstance(Object target, JdbcConnectionHolder holder) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibTransactionalDogService(holder, target));
        return enhancer.create();
    }
}
