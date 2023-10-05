package com.example.flightapp.controller;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.service.DelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fmm/api/delays")
public class DelayController {
    @Autowired
    private DelayService delayService;

    @GetMapping
    private ResponseEntity<DelayDTO> getAllDelay(){
        return delayService.getAllDelay();
    }


}
