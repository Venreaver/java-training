package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.dao.JdbcConnectionHolder;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.reflect.Proxy.newProxyInstance;

@RequiredArgsConstructor
public class TransactionalProxy implements InvocationHandler {
    private final JdbcConnectionHolder holder;
    private final Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Connection connection = holder.getConnection();
            connection.setAutoCommit(false);
            try {
                Object result = method.invoke(target, args);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            holder.closeConnection();
        }
    }

    public static Object init(Object target, JdbcConnectionHolder holder) {
        return newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new TransactionalProxy(holder, target));
    }
}
