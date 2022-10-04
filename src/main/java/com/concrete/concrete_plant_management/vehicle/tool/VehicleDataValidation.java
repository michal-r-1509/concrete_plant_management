package com.concrete.concrete_plant_management.vehicle.tool;

import com.concrete.concrete_plant_management.domain.Vehicle;
import com.concrete.concrete_plant_management.vehicle.dto.VehicleRequestDTO;

public class VehicleDataValidation {

    public Vehicle vehicleValidation(VehicleRequestDTO toValidate){
        return Vehicle.builder()
                .name(toValidate.getName() == null ? "" : toValidate.getName().trim())
                .type(toValidate.getType())
                .regNo(regNoValidating(toValidate.getRegNo()))
                .capacity(toValidate.getType() == VehicleType.PUMP ? 0.0 : toValidate.getCapacity())
                .pumpLength(toValidate.getType() == VehicleType.MIXER ? 0.0 : toValidate.getPumpLength())
                .build();
    }

    private String regNoValidating(String regNo) {
        String result = regNo.toUpperCase();
        return result.replaceAll("\\s+", "");
    }

}
