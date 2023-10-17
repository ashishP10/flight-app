package com.example.flightapp.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FlightDTO {

    private Long id;
    @NotNull(message = "Flight number is required")
    private Integer flightNumber;
    @NotNull(message = "Tail number is required")
    private String tailNumber;
    @NotNull(message = "origin is required")
    @Size(min = 3, max = 3, message = "Origin must be 3 letters")
    private String origin;
    @NotNull(message = "Destination is required")
    @Size(min = 3, max = 3, message = "Destination must be 3 letters")
    private String destination;
    @NotNull(message = "IropStatus is required")
    @Size(min = 2, max = 2, message = "iropStatus must be 2 letters")
    private String iropStatus;
    @NotNull(message = "TotalSeats is required")
    private Integer totalSeats;
    @NotNull(message = "Business class is required")
    private boolean hasBusinessClass;
    @NotNull(message = "Passengers details is required")
    private List<PassengerDTO> passengers;
    @NotNull(message = "Delay is required")
    private DelayDTO delay;

}

