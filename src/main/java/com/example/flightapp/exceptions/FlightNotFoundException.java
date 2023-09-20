package com.example.flightapp.exceptions;

public class FlightNotFoundException extends RuntimeException{
    public FlightNotFoundException(Long id) {
        super("Flight not found with id : " + id + ", please provide the correct id");
    }
}
