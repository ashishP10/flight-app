package com.example.flightapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DelayDTO {
    private Long id;
    private int code;
    private String reason;
    private int time;

}
