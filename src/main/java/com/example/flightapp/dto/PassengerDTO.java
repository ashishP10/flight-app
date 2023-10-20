package com.example.flightapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerDTO {
    private Long id;
    @NotNull(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    private String lastName;
    @NotNull(message = "Age is required")
    private Integer age;
    @NotNull(message = "Passport number is required")
    private String passportNo;
    @NotNull(message = "Country is required")
    private String country;
}
