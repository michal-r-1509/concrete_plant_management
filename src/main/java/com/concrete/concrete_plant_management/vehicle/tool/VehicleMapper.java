package com.concrete.concrete_plant_management.vehicle.tool;

import com.concrete.concrete_plant_management.domain.Vehicle;
import com.concrete.concrete_plant_management.vehicle.dto.VehicleRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class VehicleMapper {

    public Vehicle newVehicleValidation(VehicleRequestDTO toValidate) {
        return Vehicle.builder()
                .name(toValidate.getName() == null ? "" : toValidate.getName().trim())
                .type(toValidate.getType())
                .regNo(regNoValidating(toValidate.getRegNo()))
                .capacity(toValidate.getType() == VehicleType.PUMP ? 0.0 : toValidate.getCapacity())
                .pumpLength(toValidate.getType() == VehicleType.MIXER ? 0.0 : toValidate.getPumpLength())
                .build();
    }

    public void existVehicleValidation(final VehicleRequestDTO toUpdate, final Vehicle vehicle) {
        vehicle.setName(toUpdate.getName());
        vehicle.setType(toUpdate.getType());
        vehicle.setRegNo(regNoValidating(toUpdate.getRegNo()));
        vehicle.setCapacity(toUpdate.getType() == VehicleType.PUMP ? 0.0 : toUpdate.getCapacity());
        vehicle.setPumpLength(toUpdate.getType() == VehicleType.MIXER ? 0.0 : toUpdate.getPumpLength());
    }

    private String regNoValidating(String regNo) {
        String result = regNo.toUpperCase();
        return result.replaceAll("\\s+", "");
    }

}
