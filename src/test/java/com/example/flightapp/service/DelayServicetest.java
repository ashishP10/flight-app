package com.example.flightapp.service;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.dto.FlightDTO;
import com.example.flightapp.model.Delay;
import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Passenger;
import com.example.flightapp.repository.DelayRepository;
import com.example.flightapp.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DelayServicetest {

    @Mock
    private DelayRepository delayRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DelayService delayService;

    public void initModel(){

    }

    @Test
    public void testGetAllFlights() {
        List<Delay> delayList=new ArrayList<>();
        Delay delay = new Delay.DelayBuilder()
                .setCode(242)
                .setReason("DL")
                .setTime(22)
                .build();
        delayList.add(delay);
        when(delayRepository.findAll()).thenReturn(delayList);

        List<DelayDTO> delaySize = delayService.getAllDelay(1,3);
        assertEquals(1, delaySize.size());
    }

}

