package com.example.flightapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Delay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Code is required")
    private Integer code;
    @NotNull(message = "Reason is required")
    private String reason;
    @NotNull(message = "Time is required")
    private Integer time;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    private Flight flight;
}
