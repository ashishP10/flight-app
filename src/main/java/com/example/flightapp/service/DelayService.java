package com.example.flightapp.service;

import com.example.flightapp.dto.DelayDTO;
import com.example.flightapp.dto.FlightDTO;
import com.example.flightapp.dto.PassengerDTO;
import com.example.flightapp.exceptions.*;
import com.example.flightapp.model.Delay;
import com.example.flightapp.model.Flight;
import com.example.flightapp.repository.DelayRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DelayService {

    @Autowired
    private DelayRepository delayRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<DelayDTO> getAllDelay(Integer pageNumber, Integer pageSize) {
        List<Delay> delayList ;
        try {
            delayList = delayRepository.findAll();
        } catch (Exception e) {
            throw new DelayServiceException("Error occurred while fetching delay.", e);
        }
        if (delayList.isEmpty()) {
            throw new NoRecordFoundException("Delay record is unavailable");
        }
        List<DelayDTO> returnList=new ArrayList<>();
        for(Delay delay:delayList)
        {
            returnList.add(convertEntityToDto(delay));
        }
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, returnList.size());
        if (returnList.isEmpty()) {
            throw new NoRecordFoundException("No delay record is available");
        }
        return returnList.subList(startIndex, endIndex);
    }
    private DelayDTO convertEntityToDto(Delay delay)
    {
        return modelMapper.map(delay, DelayDTO.class);
    }
    private Delay convertDtoToEntity(DelayDTO delayDTO)
    {
        return modelMapper.map(delayDTO, Delay.class);
    }
    public DelayDTO createDelay(DelayDTO delayDTO)
    {
        Delay delay = convertDtoToEntity(delayDTO);
        return convertEntityToDto(delayRepository.save(delay));
    }

    public DelayDTO updateDelay(Long id, DelayDTO updatedDelayDTO) {
        Optional<Delay> existingDelayOptional = delayRepository.findById(id);
        if (existingDelayOptional.isPresent()) {
            Delay existingFlight = existingDelayOptional.get();
            updateDelayFromDTO(existingFlight, updatedDelayDTO);
              try {
                Delay savedDelay = delayRepository.save(existingFlight);
                return convertEntityToDto(savedDelay);
            } catch (Exception e) {
                  throw new DelayServiceException("Error occurred while fetching delay.", e);
            }
        }else {
            throw new NoRecordFoundException("Delay record not found for id :"+ id+ ", please provide correct id.");
        }

    }
    private void updateDelayFromDTO(Delay delay, DelayDTO delayDTO) {
        delay.setTime(delayDTO.getTime());
        delay.setReason(delayDTO.getReason());
        delay.setCode(delayDTO.getCode());

    }
    public DelayDTO getDelayById(Long id) {
        Optional<Delay> delayOptional = delayRepository.findById(id);
        if (delayOptional.isPresent()) {
            return convertEntityToDto(delayOptional.get());
        } else {
            throw new FlightNotFoundException(id);
        }
    }

//    public boolean deleteDelay(Long id) {
//        List<Delay> delays ;
//        try{
//            delays = delayRepository.findAll();
//        }catch (Exception e)
//        {
//            throw new NoRecordFoundException("No record is present for the id:" + id);
//        }
//        for(Delay delay:delays)
//        {
//            if(delay.getId().equals(id))
//            {   delay.setDeleted(true);
//                try {
//                    delayRepository.save(delay);
//                    return true;
//                } catch (Exception e) {
//                    throw new DelayServiceException("Error occurred while updating flight.", e);
//                }
//            }
//        }
//        return false;
//    }

}
