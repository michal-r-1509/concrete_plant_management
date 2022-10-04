package com.concrete.concrete_plant_management.order.dto;

import lombok.Getter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class OrderRequestDTO {
    @NotNull(message = "date cannot be null")
    private LocalDate date;
    @NotNull(message = "time cannot be null")
    private LocalTime time;
    @DecimalMin("0.0")
    @DecimalMax("1000.0")
    private double amount;
    private String concreteClass;
    private String siteAddress;
    private String description;
    private boolean pump;
    private long clientId;
}
