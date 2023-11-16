package com.example.flightapp.service;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.dto.FlightDTO;
import com.example.flightapp.dto.PassengerDTO;
import com.example.flightapp.model.Delay;
import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Passenger;
import com.example.flightapp.repository.DelayRepository;
import com.example.flightapp.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PassengerServiceTests {

    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PassengerService passengerService;
    public PassengerDTO initDTO(Passenger passenger){
        PassengerDTO passengerDto = new PassengerDTO();
        passengerDto.setAge(21);
        passengerDto.setFirstName("Ashish");
        passengerDto.setLastName("Ranjan");
        passengerDto.setPassportNo("ABC");
        passengerDto.setId(22L);
        passengerDto.setCountry("India");
        return passengerDto;
    }
    public Passenger initModel(PassengerDTO passengerDTO){
        Passenger passenger = new Passenger();
        passenger.setAge(21);
        passenger.setFirstName("Ashish");
        passenger.setLastName("Ranjan");
        passenger.setPassportNo("ABC");
        passenger.setId(22L);
        passenger.setCountry("India");
        return passenger;
    }




    @Test
    public void testGetAllPassengers() {
        List<Passenger> passengerList=new ArrayList<>();
        Passenger passenger= initModel(new PassengerDTO());
        passengerList.add(passenger);
        when(passengerRepository.findAll()).thenReturn(passengerList);
        List<PassengerDTO> passengerDTOList = passengerService.getAllPassengers(1,3);
        assertEquals(1, passengerDTOList.size());
    }
    @Test
    public void testGetPassengerById() {
      Long passengerId = 22L;
      Passenger passenger =initModel(new PassengerDTO());
        PassengerDTO passengerDto = new PassengerDTO();
        passengerDto.setAge(21);
        passengerDto.setFirstName("Ashish");
        passengerDto.setLastName("Ranjan");
        passengerDto.setPassportNo("ABC");
        passengerDto.setId(22L);
        passengerDto.setCountry("India");

        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        when(modelMapper.map(passenger, PassengerDTO.class)).thenReturn(passengerDto);
        PassengerDTO passengerDtoReturn = passengerService.getPassengerById(passengerId);
        assertEquals(passenger.getAge(), passengerDtoReturn.getAge());
    }

    @Test
    public void testCreatePassenger() {

        PassengerDTO passengerDTO= initDTO(new Passenger());
        Passenger passenger=initModel(new PassengerDTO());
        when(modelMapper.map(passengerDTO, Passenger.class)).thenReturn(passenger);
        when(modelMapper.map(passenger, PassengerDTO.class)).thenReturn(passengerDTO);
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        PassengerDTO servicePassenger = passengerService.createPassenger(passengerDTO);
        assertNotNull(servicePassenger);
        assertEquals("Ashish", servicePassenger.getFirstName());
    }

    @Test
    public void testUpdatePassenger() {
        Long passengerID = 1L;
       Passenger passenger= initModel(new PassengerDTO());
        PassengerDTO passengerDTO=initDTO(new Passenger());
        when(passengerRepository.findById(passengerID)).thenReturn(Optional.of(passenger));
        when(modelMapper.map(passenger, PassengerDTO.class)).thenReturn(passengerDTO);
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        PassengerDTO savedPassenger = passengerService.updatePassenger(passengerID,passengerDTO);
        assertNotNull(savedPassenger);
        assertEquals(passenger.getFirstName(), savedPassenger.getFirstName());
    }
    @Test
    public void testDeletePassenger() {

//        Long passengerId = 1L;
//        Passenger passenger= initModel(new PassengerDTO());
//        List<Passenger> mockPassenger = new ArrayList<>();
//        mockPassenger.add(passenger);
//        when(passengerRepository.findAll()).thenReturn(mockPassenger);
//        when(passengerRepository.save(passenger)).thenReturn(passenger);
//        boolean deleted = passengerService.deletePassenger(passengerId);
//        assertEquals(deleted, true);
    }
}
