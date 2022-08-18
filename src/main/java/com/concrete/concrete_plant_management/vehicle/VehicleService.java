package com.concrete.concrete_plant_management.vehicle;

import com.concrete.concrete_plant_management.exceptions.ElementConflictException;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order_batch.OrderBatchService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class VehicleService{

    private final VehicleCustomMethods repository;
    private final OrderBatchService orderBatchService;

    public VehicleService(final VehicleCustomMethods repository,
                          final OrderBatchService orderBatchService) {
        this.repository = repository;
        this.orderBatchService = orderBatchService;
    }

    Vehicle saveVehicle(final Vehicle toSave) {
        new VehicleDataValidation().vehicleDataValidation(toSave);
        if (repository.existsByRegNo(toSave.getRegNo())) {
            throw new ElementConflictException("vehicle with registry number", toSave.getRegNo());
        }
        return repository.save(toSave);
    }

    Vehicle updateVehicle(final int id, final Vehicle toUpdate) {
        if (repository.existsById(id)) {
            new VehicleDataValidation().vehicleDataValidation(toUpdate);
            return repository.save(toUpdate);
        }
        throw new ElementNotFoundException("vehicle", id);
    }

    public Vehicle readVehicle(final int id) {
        return repository.findById(id).orElseThrow(() -> new ElementNotFoundException("vehicle", id));
    }

    List<Vehicle> readAllVehicles() {
        return repository.findAll();
    }

    public List<Vehicle> readAllVehicles(final Sort sort) {
        return repository.findAll(sort);
    }

    void deleteVehicle(final int id) {
        if (repository.existsById(id)){
            if (orderBatchService.existsOrderBatchByVehicleId(id)){
                throw new ElementConflictException("vehicle in orderBatch");
            }else{
                repository.deleteById(id);
            }
        }else{
            throw new ElementNotFoundException("vehicle", id);
        }
    }

}
