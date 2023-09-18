package com.example.flightapp.repository;

import com.example.flightapp.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

}
