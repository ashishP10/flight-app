package com.example.flightapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.dto.FlightDTO;
import com.example.flightapp.dto.PassengerDTO;
import com.example.flightapp.model.Delay;
import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Passenger;
import com.example.flightapp.repository.FlightRepository;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class FlightServiceTests {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FlightService flightService;

    public FlightDTO initDTO( FlightDTO flight1){
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
        flight1.setTailNumber("123");
        flight1.setTotalSeats(21);
        return flight1;
    }
    public Flight initModel( Flight flight1){
        flight1.setIropStatus("DL");
        flight1.setId(1L);
        flight1.setFlightNumber(321);
        Delay delay=new Delay();
        delay.setCode(12);
        delay.setTime(1);
        delay.setReason("XYZ");
        flight1.setDelay(delay);
        Passenger passenger = new Passenger();
        passenger.setAge(21);
        passenger.setFirstName("Ashish");
        passenger.setLastName("Ranjan");
        passenger.setPassportNo("ABC");
        passenger.setId(22L);
        passenger.setCountry("India");
        List<Passenger> passengerList=new ArrayList<>();
        passengerList.add(passenger);
        flight1.setPassengers(passengerList);
        flight1.setDestination("LAS");
        flight1.setOrigin("BOE");
        flight1.setHasBusinessClass(true);
        flight1.setTailNumber("123");
        flight1.setTotalSeats(21);
        return flight1;
    }



    @Test
    public void testGetAllFlights() {
        List<Flight> mockFlights = new ArrayList<>();
        Flight flight1 =new Flight();
        flight1.setIropStatus("DL");flight1.setIropStatus("DL");
        flight1.setId(123L);
        flight1.setFlightNumber(321);
        Delay delay=new Delay();
        delay.setCode(12);
        delay.setTime(1);
        delay.setReason("XYZ");
        delay.setId(1L);
        flight1.setDelay(delay);
        Passenger passenger = new Passenger();
        passenger.setFlight(flight1);
        passenger.setAge(21);
        passenger.setFirstName("Ashish");
        passenger.setLastName("Ranjan");
        passenger.setPassportNo("ABC");
        passenger.setId(22L);
        passenger.setCountry("India");
        List<Passenger> passengerList=new ArrayList<>();
        passengerList.add(passenger);
        flight1.setPassengers(passengerList);
        flight1.setDeleted(false);
        flight1.setDestination("LAS");
        flight1.setOrigin("BOE");
        flight1.setHasBusinessClass(true);
        flight1.setTailNumber("220NV");
        flight1.setTotalSeats(21);
        mockFlights.add(flight1);
        when(flightRepository.findAll()).thenReturn(mockFlights);

        List<FlightDTO> flights = flightService.getAllFlights(1,3);
        assertEquals(1, flights.size());
    }

    @Test
    public void testGetAllCancelFlights() {
        List<Flight> mockFlights = new ArrayList<>();
        mockFlights.add(initModel(new Flight()));
        when(flightRepository.findAll()).thenReturn(mockFlights);
        List<FlightDTO> flights = flightService.getAllCancelFlights(1,3);
        assertEquals(1, flights.size());
    }

    @Test
    public void testGetFlightById() {
        Long flightId = 1L;
        Flight mockFlight = initModel(new Flight());
        mockFlight.setId(flightId);
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(mockFlight));
        when(modelMapper.map(mockFlight, FlightDTO.class)).thenReturn(new FlightDTO());
        FlightDTO flight = flightService.getFlightById(flightId);
        assertEquals(flightId, flight.getId());
    }
    @Test
    public void testCreateFlight() {
        FlightDTO flightDTO = initDTO(new FlightDTO());
        flightDTO.setTailNumber("123");
        flightDTO.setOrigin("JFK");
        flightDTO.setDestination("LAX");
        flightDTO.setIropStatus("CX");
        flightDTO.setTotalSeats(150);
        flightDTO.setHasBusinessClass(true);
        Flight mockFlight = initModel(new Flight());
        Flight flight = initModel(new Flight());
        when(modelMapper.map(flightDTO, Flight.class)).thenReturn(mockFlight);
        when(modelMapper.map(flight, FlightDTO.class)).thenReturn(flightDTO);
         when(flightRepository.save(mockFlight)).thenReturn(mockFlight);
        FlightDTO createdFlight = flightService.createFlight(flightDTO);
     assertNotNull(createdFlight);
        assertEquals("BOE", createdFlight.getOrigin());
    }

    @Test
    public void testUpdateFlight() {
        Long flightId = 1L;
        Flight updatedFlight = new Flight();
        updatedFlight.setId(flightId);
        FlightDTO updatedFlightDto = initDTO(new FlightDTO());
        Flight mockFlight = initModel(new Flight());
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(mockFlight));
        when(modelMapper.map(mockFlight, FlightDTO.class)).thenReturn(initDTO(new FlightDTO()));
        when(flightRepository.save(mockFlight)).thenReturn(mockFlight);
        FlightDTO savedFlight = flightService.updateFlight(flightId, updatedFlightDto);
        assertNotNull(savedFlight);
        assertEquals(flightId, savedFlight.getId());
    }
    @Test
    public void testDeleteFlight() {

        Long flightId = 1L;
        Flight mockFlight = new Flight();
        mockFlight.setId(flightId);
        List<Flight> mockFlights = new ArrayList<>();
        mockFlights.add(mockFlight);
        when(flightRepository.findAll()).thenReturn(mockFlights);
        when(flightRepository.save(mockFlight)).thenReturn(mockFlight);
        boolean deleted = flightService.deleteFlight(flightId);
        assertTrue(deleted);
        assertTrue(mockFlight.isDeleted());
    }
}

