package com.epam.javatraining.dogapp.aspectj;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public aspect Logger {
    pointcut callAtControllerMethods():within(Log);

    before(): callAtControllerMethods() {
        log.info("Before - '{}'", thisJoinPoint.toShortString());
    }

    after(): callAtControllerMethods() {
        log.info("After - '{}'", thisJoinPoint.toShortString());
    }
}
