package com.example.flightapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    private List<Passenger> passengers;
    @OneToOne(cascade = CascadeType.ALL)
    private Delay delay;
    private boolean isDeleted = false;

}
