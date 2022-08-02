package com.concrete.concrete_plant_management.vehicle;

import com.concrete.concrete_plant_management.order_batch.OrderBatchService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository repository;
    private final OrderBatchService orderBatchService;

    public VehicleService(final VehicleRepository repository,
                          final OrderBatchService orderBatchService) {
        this.repository = repository;
        this.orderBatchService = orderBatchService;
    }

    Vehicle saveVehicle(final Vehicle toSave) {
        if (repository.existsByRegNo(toSave.getRegNo())){
            return null;
        }
        new VehicleDataValidation().vehicleDataValidation(toSave);
        return repository.save(toSave);
    }

    Vehicle vehicleUpdate(final int id, final Vehicle toUpdate) {
        if (!repository.existsById(id)) {
            return null;
        }
        new VehicleDataValidation().vehicleDataValidation(toUpdate);
        return repository.save(toUpdate);
    }

    Vehicle readVehicle(final int id) {
        return repository.findById(id).orElse(null);
    }

    List<Vehicle> readAllVehicles() {
        return repository.findAll();
    }

    public List<Vehicle> readAllVehicles(final Sort sort) {
        return repository.findAll(sort);
    }

    boolean deleteVehicle(final int id) {
        if (repository.existsById(id) && !orderBatchService.existsOrderBatchByVehicleId(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
