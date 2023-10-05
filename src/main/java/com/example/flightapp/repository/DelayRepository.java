package com.example.flightapp.repository;

import com.example.flightapp.model.Delay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelayRepository extends JpaRepository<Delay, Long> {
}
