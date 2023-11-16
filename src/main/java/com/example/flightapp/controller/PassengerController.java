package com.example.flightapp.controller;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.dto.FlightDTO;
import com.example.flightapp.dto.PassengerDTO;
import com.example.flightapp.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fmm/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllFlights(
            @RequestParam(value = "pageNumber",defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize)  {
        List<PassengerDTO> flights = passengerService.getAllPassengers(pageNumber,pageSize);
        return ResponseEntity.ok(flights);
    }
    @PostMapping
    public ResponseEntity<?> createFlight(@Valid @RequestBody PassengerDTO passengerDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors()
                    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        PassengerDTO createdFlight = passengerService.createPassenger(passengerDTO);
        return ResponseEntity.ok(createdFlight);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDelay(@PathVariable Long id, @Valid @RequestBody PassengerDTO passengerDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors()
                    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        PassengerDTO updatedPassenger = passengerService.updatePassenger(id, passengerDTO);
        return ResponseEntity.ok(updatedPassenger);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable Long id) {
        PassengerDTO passengerDTO = passengerService.getPassengerById(id);
        if (passengerDTO != null) {
            return ResponseEntity.ok(passengerDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable Long id) {
        boolean deleted = passengerService.deletePassenger(id);
        if (deleted) {
            return ResponseEntity.ok("Delay row deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
