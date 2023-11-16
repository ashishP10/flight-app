package com.example.flightapp.controller;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.dto.FlightDTO;
import com.example.flightapp.dto.PassengerDTO;
import com.example.flightapp.service.DelayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fmm/api/delays")
public class DelayController {
    @Autowired
    private DelayService delayService;

    @GetMapping
    public ResponseEntity<List<DelayDTO>> getAllDelay(
            @RequestParam(value = "pageNumber",defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize)  {
        List<DelayDTO> delayDTO = delayService.getAllDelay(pageNumber,pageSize);
        return ResponseEntity.ok(delayDTO);
    }
    @PostMapping
    public ResponseEntity<?> createDelay(@Valid @RequestBody DelayDTO delayDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors()
                    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        DelayDTO createdFlight = delayService.createDelay(delayDTO);
        return ResponseEntity.ok(createdFlight);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDelay(@PathVariable Long id, @Valid @RequestBody DelayDTO delayDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors()
                    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        DelayDTO updatedDelay = delayService.updateDelay(id, delayDTO);
        return ResponseEntity.ok(updatedDelay);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DelayDTO> getDelayById(@PathVariable Long id) {
        DelayDTO delay = delayService.getDelayById(id);
        if (delay != null) {
            return ResponseEntity.ok(delay);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDelay(@PathVariable Long id) {
        boolean deleted = delayService.deleteDelay(id);
        if (deleted) {
            return ResponseEntity.ok("Delay row deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
