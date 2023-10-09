package com.example.flightapp.service;

import com.example.flightapp.dto.DelayDTO;
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

    public List<DelayDTO> getAllDelay()
    {
        List<Delay> delayList=delayRepository.findAll();
        List<DelayDTO> delayDTOList= new ArrayList<>();
        for(Delay delay: delayList)
        {
            delayDTOList.add(convertEntityToDto(delay));
        }
        return delayDTOList;
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

    public DelayDTO updateDelay(Long id,DelayDTO delayDTO)
    {
        Optional<Delay> delay=delayRepository.findById(id);
        DelayDTO returnDto = null;
        if(delay.isPresent())
        {
           returnDto=convertEntityToDto(delayRepository.save(convertDtoToEntity(delayDTO)));
        }
        return returnDto;
    }


}
