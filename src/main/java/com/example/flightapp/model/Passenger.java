package com.example.flightapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id")
    private Flight flight;



}
