package com.concrete.concrete_plant_management.vehicle.service;

import com.concrete.concrete_plant_management.domain.Vehicle;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.vehicle.repository.VehicleRepositoryMethods;
import org.springframework.stereotype.Component;

@Component
public class VehicleGlobalDao {
    private final VehicleRepositoryMethods repository;

    public VehicleGlobalDao(final VehicleRepositoryMethods repository) {
        this.repository = repository;
    }

    public Vehicle getVehicle(int id){
        return repository.findById(id).orElseThrow(() -> new ElementNotFoundException("vehicle", id));
    }
}
