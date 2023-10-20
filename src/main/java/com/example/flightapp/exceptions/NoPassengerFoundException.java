package com.example.flightapp.exceptions;

public class NoPassengerFoundException extends RuntimeException{


    public NoPassengerFoundException() {
        super("No passenger found");
    }
}

