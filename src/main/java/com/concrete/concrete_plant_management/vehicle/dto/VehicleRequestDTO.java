package com.concrete.concrete_plant_management.vehicle.dto;

import com.concrete.concrete_plant_management.vehicle.tool.VehicleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    @Size(min = 6, max = 8)
    private String regNo;
    private double capacity;
    private double pumpLength;
}
