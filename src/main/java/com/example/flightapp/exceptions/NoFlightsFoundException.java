package com.example.flightapp.exceptions;

public class NoFlightsFoundException extends RuntimeException {

    public NoFlightsFoundException() {
        super("No flights found");
    }
}