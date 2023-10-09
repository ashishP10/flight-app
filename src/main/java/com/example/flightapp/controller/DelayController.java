package com.example.flightapp.controller;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.service.DelayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fmm/api/delays")
public class DelayController {
    @Autowired
    private DelayService delayService;

    @GetMapping
    public ResponseEntity<List<DelayDTO>> getAllDelay(){
        List<DelayDTO> delayDTO = delayService.getAllDelay();
        return ResponseEntity.ok(delayDTO);
    }

    @PostMapping
    public ResponseEntity<DelayDTO> createDelay(@Valid @RequestBody DelayDTO delayDTO) {
        return ResponseEntity.ok(delayService.createDelay(delayDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DelayDTO> updateDelay(@PathVariable Long id,@Valid @RequestBody DelayDTO delayDTO){
            return ResponseEntity.ok(delayService.updateDelay(id,delayDTO));
    }


}
