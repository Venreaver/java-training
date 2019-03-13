package com.epam.javatraining.dogapp.aspect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public aspect Logger {
    pointcut callAtControllerMethods():
            execution(* com.epam.javatraining.dogapp.controller.DogController.*(..));

    before(): callAtControllerMethods() {
        log.info("Before - '{}'", thisJoinPoint.toShortString());
    }

    after(): callAtControllerMethods() {
        log.info("After - '{}'", thisJoinPoint.toShortString());
    }
}
