package com.example.flightapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DelayDTO {
    private Long id;
    @NotNull(message = "Code is required")
    private int code;
    @NotNull(message = "Reason is required")
    private String reason;
    @NotNull(message = "Time is required")
    private int time;

}
