package com.example.flightapp.repository;

import com.example.flightapp.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengersRepository extends JpaRepository<Passenger, Long> {
}
