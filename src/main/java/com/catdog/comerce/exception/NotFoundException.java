package com.catdog.comerce.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException(String entity, Long id) {
        super(String.format("Resource not found: %s with id %d",entity,id));
    }
}
