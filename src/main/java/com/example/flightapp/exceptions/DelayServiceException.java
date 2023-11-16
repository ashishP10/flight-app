package com.example.flightapp.exceptions;

public class DelayServiceException extends RuntimeException{
    public DelayServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

