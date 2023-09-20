package com.example.flightapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max = 3, message = "Origin must be 3 letters")
    private String origin;
    @Size(min = 3, max = 3, message = "Destination must be 3 letters")
    private String destination;
    @Size(min = 2, max = 2, message = "iropStatus must be 2 letters")
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
