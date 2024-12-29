package com.catdog.comerce.exception;


public class RepeatedException extends RuntimeException{
    public RepeatedException(String entity,String value) {
        super(String.format("%s is repeated: %s",entity,value));
    }
}