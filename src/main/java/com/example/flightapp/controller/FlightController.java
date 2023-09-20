package com.example.flightapp.controller;

import com.example.flightapp.dto.FlightDTO;
import com.example.flightapp.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fmm/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightsService;
    @PostMapping
    public ResponseEntity<FlightDTO> createFlight(@RequestBody FlightDTO flightDTO) {
        FlightDTO createdFlight = flightsService.createFlight(flightDTO);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDTO> updateFlight(@PathVariable Long id, @RequestBody FlightDTO updatedFlightDTO) {
        FlightDTO flight = flightsService.updateFlight(id, updatedFlightDTO);

        if (flight != null) {
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<FlightDTO> getAllFlights() {
        return flightsService.getAllFlights();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getFlightById(@PathVariable Long id) {
        FlightDTO flightDTO = flightsService.getFlightById(id);
        if (flightDTO != null) {
            return ResponseEntity.ok(flightDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight not found");
        }
    }
    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        flightsService.deleteFlight(id);
    }

}
