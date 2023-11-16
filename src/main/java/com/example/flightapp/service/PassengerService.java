package com.example.flightapp.service;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.dto.PassengerDTO;
import com.example.flightapp.exceptions.*;
import com.example.flightapp.model.Delay;
import com.example.flightapp.model.Passenger;
import com.example.flightapp.repository.PassengerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    public static final Integer maxPassengerAge=90;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<PassengerDTO> getAllPassengers(Integer pageNumber, Integer pageSize) {
        List<Passenger> passengerList ;
        try {
            passengerList = passengerRepository.findAll();
        } catch (Exception e) {
            throw new FlightServiceException("Error occurred while fetching flights.", e);
        }
        if (passengerList.isEmpty()) {
            throw new NoPassengerFoundException();
        }
        List<PassengerDTO> returnList=new ArrayList<>();
        for(Passenger passenger:passengerList)
        {
            returnList.add(convertEntityToDto(passenger));
        }
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, returnList.size());
        if (returnList.isEmpty()) {
            throw new NoRecordFoundException("Passenger record is unavailable");
        }
        return returnList.subList(startIndex, endIndex);
    }
    private PassengerDTO convertEntityToDto(Passenger passenger) {
        return modelMapper.map(passenger, PassengerDTO.class);
    }
    private Passenger convertDtoToEntity(PassengerDTO PassengerDTO) {
        return modelMapper.map(PassengerDTO, Passenger.class);
    }
    public PassengerDTO createPassenger(PassengerDTO passengerDTO) {
        int l = passengerDTO.getAge();
        if (l > maxPassengerAge)
            throw new PassengerAgeRestriction();
        else {
            try {
                Passenger passenger = convertDtoToEntity(passengerDTO);
                Passenger savedFlight = passengerRepository.save(passenger);
                return convertEntityToDto(savedFlight);
            } catch (Exception e) {
                throw new FlightServiceException("Error occurred while creating passenger data.", e);
            }
        }
    }
    public PassengerDTO updatePassenger(Long id, PassengerDTO passengerDTO) {
        Optional<Passenger> existingPassengerOptional = passengerRepository.findById(id);
        if (existingPassengerOptional.isPresent()) {
            Passenger existingPassenger= existingPassengerOptional.get();
            updatePssengerFromDTO(existingPassenger, passengerDTO);
            try {
                Passenger passenger = passengerRepository.save(existingPassenger);
                return convertEntityToDto(passenger);
            } catch (Exception e) {
                throw new DelayServiceException("Error occurred while fetching delay.", e);
            }
        }else {
            throw new NoRecordFoundException("Delay record not found for id :"+ id+ ", please provide correct id.");
        }

    }
    public PassengerDTO getPassengerById(Long id) {
        Optional<Passenger> passengerOptional = passengerRepository.findById(id);
        if (passengerOptional.isPresent()) {
            return convertEntityToDto(passengerOptional.get());
        } else {
            throw new NoRecordFoundException("No record found for the id: "+id);
        }
    }
    public boolean deletePassenger(Long id) {
        List<Passenger> passengerList ;
        try{
            passengerList = passengerRepository.findAll();
        }catch (Exception e)
        {
            throw new NoRecordFoundException("No record is present for the id:" + id);
        }
        for(Passenger passenger:passengerList)
        {
            if(passenger.getId().equals(id))
            {   passenger.setDeleted(true);
                try {
                    passengerRepository.save(passenger);
                    return true;
                } catch (Exception e) {
                    throw new DelayServiceException("Error occurred while deleting Passenger.", e);
                }
            }
        }
        return false;
    }
    public void updatePssengerFromDTO(Passenger passenger,PassengerDTO passengerDTO){
        passenger.setCountry(passengerDTO.getCountry());
        passenger.setAge(passengerDTO.getAge());
        passenger.setLastName(passengerDTO.getLastName());
        passenger.setFirstName(passenger.getFirstName());
        passenger.setPassportNo(passengerDTO.getPassportNo());
    }

}
