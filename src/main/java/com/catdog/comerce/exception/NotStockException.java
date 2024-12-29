package com.catdog.comerce.exception;

public class NotStockException extends RuntimeException{
    public NotStockException(String message) {
        super(message);
    }
}
