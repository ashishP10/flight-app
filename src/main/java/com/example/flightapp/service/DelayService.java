package com.example.flightapp.service;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.repository.DelayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelayService {

    @Autowired
    private DelayRepository delayRepository;


    private DelayDTO getAllDelay()
    {

        return null;

    }
}
