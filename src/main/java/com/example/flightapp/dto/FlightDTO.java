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

    private String tailNumber;
    @Size(min = 3, max = 3, message = "Origin must be 3 letters")
    private String origin;
    @Size(min = 3, max = 3, message = "Destination must be 3 letters")
    private String destination;
    @Size(min = 2, max = 2, message = "iropStatus must be 2 letters")
    private String iropStatus;
    private Integer totalSeats;
    private boolean hasBusinessClass;
    private List<PassengerDTO> passengers;
    private DelayDTO delay;
}
