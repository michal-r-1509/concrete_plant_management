package com.concrete.concrete_plant_management.vehicle.dto;

import com.concrete.concrete_plant_management.vehicle.tool.VehicleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class VehicleRequestDTO {
    private int id;
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    private VehicleType type;
    @NotBlank
    @Size(min = 2, max = 10)
    private String regNo;
    @DecimalMin("0.0")
    @DecimalMax("30.0")
    private double capacity;
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private double pumpLength;
}
