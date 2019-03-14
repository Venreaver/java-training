package com.epam.javatraining.dogapp.aspectj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Log {
}
