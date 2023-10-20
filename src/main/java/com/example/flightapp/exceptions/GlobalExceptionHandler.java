package com.example.flightapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<String> handleFlightNotFoundException(FlightNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoRecordFoundException.class)
    public ResponseEntity<String> handleNoFlightsFoundException(NoRecordFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PassengerLimitExceedException.class)
    public ResponseEntity<String> handlePassengerLimitExceedException(PassengerLimitExceedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(NoPassengerFoundException.class)
    public ResponseEntity<String> handleNoPassengerFoundException(NoPassengerFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(PassengerAgeRestriction.class)
    public ResponseEntity<String> handlePassengerAgeRestriction(PassengerAgeRestriction ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
