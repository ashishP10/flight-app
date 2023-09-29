package com.example.flightapp.controller;

import com.example.flightapp.dto.FlightDTO;
import com.example.flightapp.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fmm/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightsService;
    @PostMapping
    public ResponseEntity<?> createFlight(@Valid @RequestBody FlightDTO flightDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        FlightDTO createdFlight = flightsService.createFlight(flightDTO);
        return ResponseEntity.ok(createdFlight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFlight(@PathVariable Long id, @Valid @RequestBody FlightDTO updatedFlightDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        FlightDTO updatedFlight = flightsService.updateFlight(id, updatedFlightDTO);
        if (updatedFlight != null) {
            return ResponseEntity.ok(updatedFlight);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights(
            @RequestParam(value = "pageNumber",defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize)  {
        List<FlightDTO> flights = flightsService.getAllFlights(pageNumber,pageSize);
        return ResponseEntity.ok(flights);
    }
    @PostMapping("/upload-csv")
    public ResponseEntity<?> uploadCsvFile(@RequestPart("file") MultipartFile file) {
        return flightsService.processCsvFile(file);
    }
    @GetMapping("/show_cancelled")
    public ResponseEntity<List<FlightDTO>> getAllFlightsData(
            @RequestParam(value = "pageNumber",defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize)  {
        List<FlightDTO> flights = flightsService.getAllCancelFlights(pageNumber,pageSize);
        return ResponseEntity.ok(flights);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
        FlightDTO flight = flightsService.getFlightById(id);
        if (flight != null) {
            return ResponseEntity.ok(flight);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        boolean deleted = flightsService.deleteFlight(id);
        if (deleted) {
            return ResponseEntity.ok("Flight deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
