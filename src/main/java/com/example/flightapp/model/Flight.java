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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private List<Passenger> passengers;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delay_id", referencedColumnName = "id")
    private Delay delay;
    private boolean isDeleted = false;

}
