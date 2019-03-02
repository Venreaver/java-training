package com.epam.javatraining.dogapp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

@Aspect
public class LogAspect extends Logging {
    @Around("within(@com.epam.javatraining.dogapp.aspect.Log *)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        String classAndMethod = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
        logExecutionTime(classAndMethod, stopWatch, Arrays.stream(joinPoint.getArgs()).collect(toList()));
        return result;
    }
}
