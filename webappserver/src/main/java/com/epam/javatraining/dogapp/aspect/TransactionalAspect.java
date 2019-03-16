package com.epam.javatraining.dogapp.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRED;

@Slf4j
@AllArgsConstructor
public class TransactionalAspect {
    private final PlatformTransactionManager txManager;

    public Object transactionalMethodInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Processing in transaction '{}'", joinPoint.toShortString());
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition(PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(definition);
        Object result;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
        txManager.commit(status);
        return result;
    }
}

