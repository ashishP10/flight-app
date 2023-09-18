package com.example.flightapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Delay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer code;
    private String reason;
    private Integer time;
    @OneToOne
    @JoinColumn
    private Flight flight;
}
