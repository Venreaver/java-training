package com.epam.javatraining.dogapp.aspect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public aspect Logger {
    pointcut callAtServiceMethods():
            execution(* com.epam.javatraining.dogapp.controller.DogServiceImpl.*(..));

    before(): callAtServiceMethods() {
        log.info("Before - '{}'", thisJoinPoint.toShortString());
    }

    after(): callAtServiceMethods() {
        log.info("After - '{}'", thisJoinPoint.toShortString());
    }
}
