package com.example.flightapp.service;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.dto.FlightDTO;
import com.example.flightapp.dto.PassengerDTO;
import com.example.flightapp.exceptions.FlightNotFoundException;
import com.example.flightapp.exceptions.NoFlightsFoundException;
import com.example.flightapp.exceptions.PassengerLimitExceedException;
import com.example.flightapp.model.Delay;
import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Passenger;
import com.example.flightapp.repository.FlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {

    public static final Integer Passengerlength=3;
    @Autowired
    private FlightRepository flightsRepository;
    @Autowired
    private ModelMapper modelMapper;
    public FlightDTO createFlight(FlightDTO flightDTO) {
        int l = flightDTO.getPassengers().size();
        if (l > Passengerlength)
            throw new PassengerLimitExceedException();
        else {
            Flight flight = convertToEntity(flightDTO);
            Flight savedFlight = flightsRepository.save(flight);
            return convertEntityToDto(savedFlight);
        }
    }
    private Flight convertToEntity(FlightDTO flightDTO) {
        return modelMapper.map(flightDTO, Flight.class);
    }
    private FlightDTO convertEntityToDto(Flight flight) {
        FlightDTO flightDTO = modelMapper.map(flight, FlightDTO.class);

        // Convert passengers to PassengerDTO
        List<PassengerDTO> passengerDTOs = flight.getPassengers().stream()
                .map(passenger -> modelMapper.map(passenger, PassengerDTO.class))
                .collect(Collectors.toList());
        flightDTO.setPassengers(passengerDTOs);

        // Convert delay to DelayDTO
        if (flight.getDelay() != null) {
            DelayDTO delayDTO = modelMapper.map(flight.getDelay(), DelayDTO.class);
            flightDTO.setDelay(delayDTO);
        }

        return flightDTO;
    }

    public FlightDTO updateFlight(Long id, FlightDTO updatedFlightDTO) {
        Optional<Flight> existingFlightOptional = flightsRepository.findById(id);
        if (existingFlightOptional.isPresent()) {
            Flight existingFlight = existingFlightOptional.get();
            updateFlightFromDTO(existingFlight, updatedFlightDTO);
            int l = existingFlight.getPassengers().size();
            if (l > Passengerlength)
                throw new PassengerLimitExceedException();
            Flight savedFlight = flightsRepository.save(existingFlight);
            return convertEntityToDto(savedFlight);
        }else {
            throw new FlightNotFoundException(id);
        }

    }
    public boolean deleteFlight(Long id) {
        List<Flight> flights = flightsRepository.findAll();
        for(Flight flight:flights)
        {
            if(flight.getId().equals(id))
            {   flight.setDeleted(true);
                flightsRepository.save(flight);
                return true;
            }
        }
        throw new FlightNotFoundException(id);
    }
    public FlightDTO getFlightById(Long id) {
        Optional<Flight> flightOptional = flightsRepository.findById(id);
        if (flightOptional.isPresent()) {
            return convertEntityToDto(flightOptional.get());
        } else {
            throw new FlightNotFoundException(id);
        }
    }
    public List<FlightDTO> getAllFlights(Integer pageNumber, Integer pageSize) {
        Pageable page= PageRequest.of(pageNumber,pageSize);
        List<Flight> flights = flightsRepository.findAll();
        if (flights.isEmpty()) {
            throw new NoFlightsFoundException();
        }
        List<FlightDTO> returnList=new ArrayList<>();
        for(Flight flight:flights)
        {
            if(!(flight.getIropStatus().equals("CX") || flight.isDeleted())){
                {
                    returnList.add(convertEntityToDto(flight));
                }}
        }
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, returnList.size());
        if (returnList.isEmpty()) {
            throw new NoFlightsFoundException();
        }
        return returnList.subList(startIndex, endIndex);
    }

    public List<FlightDTO> getAllCancelFlights(Integer pageNumber, Integer pageSize) {
        List<Flight> flights = flightsRepository.findAll();
        if (flights.isEmpty()) {
            throw new NoFlightsFoundException();
        }
        List<FlightDTO> returnList=new ArrayList<>();
        for(Flight flight:flights)
        {
            if(!(flight.isDeleted())){
                {
                    returnList.add(convertEntityToDto(flight));
                }}
        }
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, returnList.size());
        if (returnList.isEmpty()) {
            throw new NoFlightsFoundException();
        }
        return returnList.subList(startIndex, endIndex);
    }
    private void updateFlightFromDTO(Flight flight, FlightDTO flightDTO) {
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setTailNumber(flightDTO.getTailNumber());
        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setIropStatus(flightDTO.getIropStatus());
        flight.setTotalSeats(flightDTO.getTotalSeats());
        flight.setHasBusinessClass(flightDTO.isHasBusinessClass());

        updatePassengersFromDTO(flight, flightDTO.getPassengers());
        updateDelayFromDTO(flight, flightDTO.getDelay());
    }

    private void updatePassengersFromDTO(Flight flight, List<PassengerDTO> passengerDTOs) {
        List<Passenger> updatedPassengers = new ArrayList<>();

        for (PassengerDTO passengerDTO : passengerDTOs) {
            Passenger passenger = new Passenger();
            passenger.setFirstName(passengerDTO.getFirstName());
            passenger.setLastName(passengerDTO.getLastName());
            passenger.setAge(passengerDTO.getAge());
            passenger.setPassportNo(passengerDTO.getPassportNo());
            passenger.setCountry(passengerDTO.getCountry());
            passenger.setFlight(flight);
            updatedPassengers.add(passenger);
        }

        flight.setPassengers(updatedPassengers);
    }
    private void updateDelayFromDTO(Flight flight, DelayDTO delayDTO) {
        if (delayDTO != null) {
            Delay delay = flight.getDelay();
            if (delay == null) {
                delay = new Delay();
            }
            delay.setCode(delayDTO.getCode());
            delay.setReason(delayDTO.getReason());
            delay.setTime(delayDTO.getTime());
            flight.setDelay(delay);
        } else {
            flight.setDelay(null);
        }
    }
}
