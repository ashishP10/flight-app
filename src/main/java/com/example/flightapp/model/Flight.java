package com.example.flightapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer flightNumber;
    private String tailNumber;
    private String origin;
    private String destination;
    private String iropStatus;
    private Integer totalSeats;
    private boolean hasBusinessClass;
    @OneToMany(mappedBy = "flight")
    private List<Passenger> passengers;
    @OneToOne(mappedBy = "flight")
    private Delay delay;

}
