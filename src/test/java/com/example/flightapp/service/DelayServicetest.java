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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void testGetAllDelays() {
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
    @Test
    public void testGetDelayById() {
        Long delayId = 1L;
        Delay delay = new Delay.DelayBuilder()
                .setCode(242)
                .setReason("DL")
                .setTime(22)
                .build();
        delay.setId(delayId);
        DelayDTO delayDTO=new DelayDTO();
        delayDTO.setCode(242);
        delayDTO.setReason("DL");
        delayDTO.setTime(22);
        when(delayRepository.findById(delayId)).thenReturn(Optional.of(delay));
        when(modelMapper.map(delay, DelayDTO.class)).thenReturn(delayDTO);
        DelayDTO delayDtoReturn = delayService.getDelayById(delayId);
        assertEquals(delay.getCode(), delayDtoReturn.getCode());
    }

    @Test
    public void testCreateDelay() {

        DelayDTO delayDTO = new DelayDTO();
        delayDTO.setCode(242);
        delayDTO.setReason("DL");
        delayDTO.setTime(22);
        Delay delay = new Delay.DelayBuilder()
                .setCode(242)
                .setReason("DL")
                .setTime(22)
                .build();
        when(modelMapper.map(delayDTO, Delay.class)).thenReturn(delay);
        when(modelMapper.map(delay, DelayDTO.class)).thenReturn(delayDTO);
        when(delayRepository.save(delay)).thenReturn(delay);
        DelayDTO createdDelay = delayService.createDelay(delayDTO);
        assertNotNull(createdDelay);
        assertEquals("DL", createdDelay.getReason());
    }
    @Test
    public void testUpdateDelay() {
        Long delayId = 1L;
        Delay delay = new Delay.DelayBuilder()
                .setCode(242)
                .setReason("DL")
                .setTime(22)
                .build();
        DelayDTO delayDTO = new DelayDTO();
        delayDTO.setCode(242);
        delayDTO.setReason("DL");
        delayDTO.setTime(22);
        when(delayRepository.findById(delayId)).thenReturn(Optional.of(delay));
        when(modelMapper.map(delay, DelayDTO.class)).thenReturn(delayDTO);
        when(delayRepository.save(delay)).thenReturn(delay);
        DelayDTO savedDelay = delayService.updateDelay(delayId,delayDTO);
        assertNotNull(savedDelay);
        assertEquals(delay.getTime(), savedDelay.getTime());
    }
    @Test
    public void testDeleteDelay() {
        Long delayId = 1L;
        Delay delay = new Delay.DelayBuilder()
                .setCode(242)
                .setReason("DL")
                .setTime(22)
                .setId(1L)
                .build();
        List<Delay> mockDelays = new ArrayList<>();
        mockDelays.add(delay);
        when(delayRepository.findAll()).thenReturn(mockDelays);
        when(delayRepository.save(delay)).thenReturn(delay);
        boolean deleted = delayService.deleteDelay(delayId);
        assertEquals(deleted, delay.isDeleted());
    }
}

