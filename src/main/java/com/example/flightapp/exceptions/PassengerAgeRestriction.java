package com.example.flightapp.exceptions;

public class PassengerAgeRestriction extends RuntimeException{
    public PassengerAgeRestriction() {
        super("Passenger age cannot be greater than 90");
    }
}
