package com.example.flightapp.controller;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.dto.FlightDTO;
import com.example.flightapp.dto.PassengerDTO;
import com.example.flightapp.model.Delay;
import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Passenger;
import com.example.flightapp.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = FlightController.class)
@ExtendWith(MockitoExtension.class)
public class FlightControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;


    public FlightDTO init( FlightDTO flight1){
        flight1.setIropStatus("DL");
        flight1.setIropStatus("DL");
        flight1.setId(1L);
        flight1.setFlightNumber(321);
        DelayDTO delay=new DelayDTO();
        delay.setCode(12);
        delay.setTime(1);
        delay.setReason("XYZ");
        flight1.setDelay(delay);
        PassengerDTO passenger = new PassengerDTO();
        passenger.setAge(21);
        passenger.setFirstName("Ashish");
        passenger.setLastName("Ranjan");
        passenger.setPassportNo("ABC");
        passenger.setId(22L);
        passenger.setCountry("India");
        List<PassengerDTO> passengerList=new ArrayList<>();
        passengerList.add(passenger);
        flight1.setPassengers(passengerList);
        flight1.setDestination("LAS");
        flight1.setOrigin("BOE");
        flight1.setHasBusinessClass(true);
        flight1.setTailNumber("220NV");
        flight1.setTotalSeats(21);
        return flight1;
    }
   @Test
    public void testGetAllFlights() throws Exception {
        List<FlightDTO> flightDTOList = Arrays.asList( init(new FlightDTO()),init(new FlightDTO()));
       when(flightService.getAllFlights(1,2)).thenReturn(flightDTOList);
       mockMvc.perform(get("/fmm/api/flights")
                       .contentType(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk());
//               .andExpect(jsonPath("$[0].origin").value("BOE"));
    }

    @Test
    public void testGetAllCancelledFlights() throws Exception {
        List<FlightDTO> flightDTOList = Arrays.asList( init(new FlightDTO()),init(new FlightDTO()));
        when(flightService.getAllFlights(1,2)).thenReturn(flightDTOList);
        mockMvc.perform(get("/fmm/api/flights//show_cancelled")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//               .andExpect(jsonPath("$[0].origin").value("BOE"));
    }

    @Test
    public void testGetFlightById() throws Exception {
        FlightDTO flightDTO = init(new FlightDTO());
        when(flightService.getFlightById(1L)).thenReturn(flightDTO);
        mockMvc.perform(get("/fmm/api/flights/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void testGetFlightByWrongId() throws Exception {
        FlightDTO flightDTO = init(new FlightDTO());
        when(flightService.getFlightById(12L)).thenReturn(flightDTO);
        mockMvc.perform(get("/fmm/api/flights/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testCreateFlight() throws Exception {
        FlightDTO flightDTO = init(new FlightDTO());
        when(flightService.createFlight(flightDTO)).thenReturn(flightDTO);
        mockMvc.perform(post("/fmm/api/flights")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(flightDTO)))
                .andExpect(status().isOk());
    }
    @Test
    public void testCreateFlightValidation() throws Exception {
        FlightDTO flightDTO = init(new FlightDTO());
        flightDTO.setOrigin("PQPQPQ");
        when(flightService.createFlight(flightDTO)).thenReturn(flightDTO);
        mockMvc.perform(post("/fmm/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flightDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateFlightValidation() throws Exception {
        FlightDTO flightDTO = init(new FlightDTO());
        flightDTO.setOrigin("PQPQPQ");
        when(flightService.createFlight(flightDTO)).thenReturn(flightDTO);
        mockMvc.perform(put("/fmm/api/flights/{id}",43)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flightDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testUpdateFlight()  throws Exception {
        FlightDTO flightDTO = init(new FlightDTO());
        when(flightService.updateFlight(21L,flightDTO)).thenReturn(flightDTO);
        mockMvc.perform(put("/fmm/api/flights/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteFlight() throws Exception {
        when(flightService.deleteFlight(1L)).thenReturn(true);
        mockMvc.perform(delete("/fmm/api/flights/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFlightWrongId() throws Exception {
        when(flightService.deleteFlight(12L)).thenReturn(true);
        mockMvc.perform(delete("/fmm/api/flights/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}


