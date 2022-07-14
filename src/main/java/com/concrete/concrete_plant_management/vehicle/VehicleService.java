package com.concrete.concrete_plant_management.vehicle;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(final VehicleRepository repository) {
        this.repository = repository;
    }

    Vehicle saveVehicle(final Vehicle toSave) {
        toSave.setRegNo(toSave.getRegNo().toUpperCase());
        return repository.save(toSave);
    }

    Vehicle vehicleUpdate(final int id, final Vehicle toUpdate) {
        if (repository.existsById(id)) {
            return repository.save(toUpdate);
        } else {
            return null;
        }
    }

    Vehicle readVehicle(final int id) {
        return repository.findById(id).orElse(null);
    }

    List<Vehicle> readAllVehicles() {
        return repository.findAll();
    }

    boolean deleteVehicle(final int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
