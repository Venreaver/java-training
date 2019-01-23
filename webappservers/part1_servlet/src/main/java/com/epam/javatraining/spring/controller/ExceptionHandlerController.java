package com.epam.javatraining.spring.controller;

import com.epam.javatraining.spring.exception.DogNotFoundException;
import com.epam.javatraining.spring.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, HttpMediaTypeException.class,
            MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception exception) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(DogNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDogNotFound(DogNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception exception) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse(exception.getMessage()));
    }
}
