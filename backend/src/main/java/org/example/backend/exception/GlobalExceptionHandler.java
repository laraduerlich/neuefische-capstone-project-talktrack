package org.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponsesDTO handleNullPointerException(NullPointerException e) {
        return new ErrorResponsesDTO(e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponsesDTO handleIOException(IOException e) {
        return new ErrorResponsesDTO(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponsesDTO handleNoSuchElementException(NoSuchElementException e) {
        return new ErrorResponsesDTO(e.getMessage());
    }

    @ExceptionHandler(Exception.class) // Fallback
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponsesDTO handleException(Exception e) {
        return new ErrorResponsesDTO(e.getMessage());
    }
}
