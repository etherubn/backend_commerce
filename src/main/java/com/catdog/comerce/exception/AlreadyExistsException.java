package com.catdog.comerce.exception;

import lombok.Data;

@Data
public class AlreadyExistsException extends RuntimeException{
    private String field1;

    public AlreadyExistsException(String entityClass,String field1) {
        super(String.format("%s already exist",entityClass));
        this.field1 = field1;
    }
}
