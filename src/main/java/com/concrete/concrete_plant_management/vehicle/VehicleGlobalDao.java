package com.concrete.concrete_plant_management.vehicle;

import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class VehicleGlobalDao {
    private final VehicleCustomMethods repository;

    public VehicleGlobalDao(final VehicleCustomMethods repository) {
        this.repository = repository;
    }

    public Vehicle getVehicle(int id){
        return repository.findById(id).orElseThrow(() -> new ElementNotFoundException("vehicle", id));
    }
}
