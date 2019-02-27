package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.dao.JdbcConnectionHolder;

import java.lang.reflect.InvocationHandler;

import static java.lang.reflect.Proxy.newProxyInstance;

public class TransactionalProxy extends TransactionalService implements InvocationHandler {
    public TransactionalProxy(JdbcConnectionHolder holder, Object target) {
        super(holder, target);
    }

    public static Object getInstance(Object target, JdbcConnectionHolder holder) {
        return newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new TransactionalProxy(holder, target));
    }
}
