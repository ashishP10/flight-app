package com.example.flightapp.exceptions;

public class FlightServiceException extends RuntimeException {
    public FlightServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}