package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.dao.JdbcConnectionHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class CglibTransactionalDogService implements InvocationHandler {
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

    public static Object getInstance(Object target, JdbcConnectionHolder holder) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibTransactionalDogService(holder, target));
        return enhancer.create();
    }
}
