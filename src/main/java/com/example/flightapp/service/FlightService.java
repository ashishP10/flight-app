package com.example.flightapp.service;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.dto.FlightDTO;
import com.example.flightapp.dto.PassengerDTO;
import com.example.flightapp.exceptions.FlightNotFoundException;
import com.example.flightapp.exceptions.FlightServiceException;
import com.example.flightapp.exceptions.NoRecordFoundException;
import com.example.flightapp.exceptions.PassengerLimitExceedException;
import com.example.flightapp.model.Delay;
import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Passenger;
import com.example.flightapp.repository.FlightRepository;
import com.opencsv.CSVReader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            try {
                Flight flight = convertToEntity(flightDTO);
                Flight savedFlight = flightsRepository.save(flight);
                return convertEntityToDto(savedFlight);
            } catch (Exception e) {
                throw new FlightServiceException("Error occurred while creating flight.", e);
            }
        }
    }
    private Flight convertToEntity(FlightDTO flightDTO) {
        return modelMapper.map(flightDTO, Flight.class);
    }
    private FlightDTO convertEntityToDto(Flight flight) {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setId(flight.getId());
        flightDTO.setFlightNumber(flight.getFlightNumber());
        flightDTO.setDestination(flight.getDestination());
        flightDTO.setTailNumber(flightDTO.getTailNumber());
        flightDTO.setHasBusinessClass(flight.isHasBusinessClass());
        flightDTO.setIropStatus(flight.getIropStatus());
        flightDTO.setOrigin(flight.getOrigin());
        // Convert passengers to PassengerDTO


        List<PassengerDTO> passengerDTOList=new ArrayList<>();
        for(Passenger passenger:flight.getPassengers())
        {
            PassengerDTO passengerDTO=new PassengerDTO();
            passengerDTO.setCountry(passenger.getCountry());
            passengerDTO.setId(passenger.getId());
            passengerDTO.setAge(passenger.getAge());
            passengerDTO.setFirstName(passenger.getFirstName());
            passengerDTO.setLastName(passenger.getLastName());
            passengerDTO.setPassportNo(passenger.getPassportNo());
            passengerDTOList.add(passengerDTO);
        }
        flightDTO.setPassengers(passengerDTOList);

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
            try {
                Flight savedFlight = flightsRepository.save(existingFlight);
                return convertEntityToDto(savedFlight);
            } catch (Exception e) {
                throw new FlightServiceException("Error occurred while updating flight.", e);
            }
        }else {
            throw new FlightNotFoundException(id);
        }

    }
    public boolean deleteFlight(Long id) {
        List<Flight> flights ;
        try{
            flights = flightsRepository.findAll();
        }catch (Exception e)
        {
            throw new FlightNotFoundException(id);
        }
        for(Flight flight:flights)
        {
            if(flight.getId().equals(id))
            {   flight.setDeleted(true);
                try {
                    flightsRepository.save(flight);
                    return true;
                } catch (Exception e) {
                    throw new FlightServiceException("Error occurred while updating flight.", e);
                }
            }
        }
        return false;
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
        List<Flight> flights ;
        try {
            flights = flightsRepository.findAll();
        } catch (Exception e) {
            throw new FlightServiceException("Error occurred while fetching flights.", e);
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
            throw new NoRecordFoundException("Flight record is unavailable");
        }
        return returnList.subList(startIndex, endIndex);
    }

    public List<FlightDTO> getAllCancelFlights(Integer pageNumber, Integer pageSize) {

        List<Flight> flights ;
        try {
            flights = flightsRepository.findAll();
        } catch (Exception e) {
            throw new FlightServiceException("Error occurred while fetching flights.", e);
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
            throw new NoRecordFoundException("Flight record is unavailable");
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
    public List<String[]> readData(InputStream inputStream) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            return reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public ResponseEntity<String> processCsvFile(MultipartFile file) {
        try {
            List<String[]> data = readData(file.getInputStream());
            data = data.subList(1, data.size()-1);
            if (data != null) {
                for (String[] nextRecord : data) {
                    FlightDTO flightDTO = new FlightDTO();
                    flightDTO.setFlightNumber(Integer.parseInt(nextRecord[0]));
                    flightDTO.setTailNumber(nextRecord[1]);
                    flightDTO.setOrigin(nextRecord[2]);
                    flightDTO.setDestination(nextRecord[3]);
                    flightDTO.setIropStatus(nextRecord[4]);
                    flightDTO.setTotalSeats(Integer.parseInt(nextRecord[5]));
                    flightDTO.setHasBusinessClass(Boolean.parseBoolean(nextRecord[6]));
                    Flight flight = convertToEntity(flightDTO);
                    try {
                        flightsRepository.save(flight);
                    } catch (Exception e) {
                        throw new FlightServiceException("Error occurred while updating flight.", e);
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("CSV data has been processed and saved.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing CSV data.");
        }

    }
}
