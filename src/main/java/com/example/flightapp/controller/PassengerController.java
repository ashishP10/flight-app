package com.example.flightapp.controller;

import com.example.flightapp.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fmm/api/flights/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

}
