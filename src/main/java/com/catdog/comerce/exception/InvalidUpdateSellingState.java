package com.catdog.comerce.exception;

public class InvalidUpdateSellingState extends RuntimeException{
    public InvalidUpdateSellingState(String message) {
        super("Invalid selling state: " + message);
    }
}
