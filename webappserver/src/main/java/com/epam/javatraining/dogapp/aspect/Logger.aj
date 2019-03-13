package com.epam.javatraining.dogapp.aspect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public aspect Logger {
    pointcut callAtControllerMethods():within(Log);

    before(): callAtControllerMethods() {
        log.debug("Before - '{}'", thisJoinPoint.toShortString());
    }

    after(): callAtControllerMethods() {
        log.debug("After - '{}'", thisJoinPoint.toShortString());
    }
}
