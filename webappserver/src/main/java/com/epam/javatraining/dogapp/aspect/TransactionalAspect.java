package com.epam.javatraining.dogapp.aspect;

import com.epam.javatraining.dogapp.dao.JdbcConnectionHolder;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@AllArgsConstructor
public class TransactionalAspect {
    private final JdbcConnectionHolder holder;

    @Around("@annotation(com.epam.javatraining.dogapp.aspect.Transactional)")
    public Object transactionalMethodInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Connection connection = holder.getConnection();
            connection.setAutoCommit(false);
            try {
                Object result = joinPoint.proceed();
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

