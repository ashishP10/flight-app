package com.example.flightapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String passportNo;
    private String country;
}
