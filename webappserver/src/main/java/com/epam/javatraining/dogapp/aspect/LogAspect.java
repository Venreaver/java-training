package com.epam.javatraining.dogapp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

public class LogAspect extends Logging {
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        String classAndMethod = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
        logExecutionTime(classAndMethod, stopWatch, Arrays.stream(joinPoint.getArgs()).collect(toList()));
        return result;
    }
}
