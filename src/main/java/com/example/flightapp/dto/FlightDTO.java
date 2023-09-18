package com.example.flightapp.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FlightDTO {

    private Long id;
    private Integer flightNumber;
    private String tailNumber;
    private String origin;
    private String destination;
    private String iropStatus;
    private Integer totalSeats;
    private boolean hasBusinessClass;
    private List<PassengerDTO> passengers;
    private DelayDTO delay;
}
