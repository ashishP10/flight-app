package com.example.flightapp.exceptions;

public class PassengerLimitExceedException extends RuntimeException{
    public PassengerLimitExceedException() {
        super("Passenger list cannot exceed 3");
    }
}
