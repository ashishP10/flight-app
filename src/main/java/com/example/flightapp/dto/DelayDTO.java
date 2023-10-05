package com.example.flightapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DelayDTO {
    @NotNull(message = "Delay code is required")
    private int code;
    @NotNull(message = "Delay reason is required")
    private String reason;
    @NotNull(message = "Delay time is required")
    private int time;

}
