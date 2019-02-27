package com.epam.javatraining.dogapp.service;

import com.epam.javatraining.dogapp.dao.JdbcConnectionHolder;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public abstract class TransactionalService {
    private final JdbcConnectionHolder holder;
    private final Object target;
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
}
