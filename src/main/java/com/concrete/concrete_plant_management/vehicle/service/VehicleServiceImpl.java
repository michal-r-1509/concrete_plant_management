package com.concrete.concrete_plant_management.vehicle.service;

import com.concrete.concrete_plant_management.domain.Vehicle;
import com.concrete.concrete_plant_management.exceptions.ElementConflictException;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.exceptions.VehicleByRegNoExistsException;
import com.concrete.concrete_plant_management.order_batch.OrderBatchService;
import com.concrete.concrete_plant_management.vehicle.tool.VehicleDataValidation;
import com.concrete.concrete_plant_management.vehicle.repository.VehicleRepositoryMethods;
import com.concrete.concrete_plant_management.vehicle.dto.VehicleRequestDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepositoryMethods repository;
    private final OrderBatchService orderBatchService;

    public VehicleServiceImpl(final VehicleRepositoryMethods repository,
                              final OrderBatchService orderBatchService) {
        this.repository = repository;
        this.orderBatchService = orderBatchService;
    }

    @Override
    public Vehicle saveVehicle(final VehicleRequestDTO toSave) {
        if (repository.existsByRegNo(toSave.getRegNo())) {
            throw new VehicleByRegNoExistsException(toSave.getRegNo());
        }
        Vehicle vehicle = new VehicleDataValidation().vehicleValidation(toSave);
        return repository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(final int id, final VehicleRequestDTO toUpdate) {
        if (repository.existsById(id)) {
            Vehicle vehicle = new VehicleDataValidation().vehicleValidation(toUpdate);
            return repository.save(vehicle);
        }
        throw new ElementNotFoundException("vehicle", id);
    }

    @Override
    public Vehicle readVehicle(final int id) {
        return repository.findById(id).orElseThrow(() -> new ElementNotFoundException("vehicle", id));
    }

    @Override
    public List<Vehicle> readAllVehicles() {
        return repository.findAll();
    }

    @Override
    public List<Vehicle> readAllVehicles(final Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public void deleteVehicle(final int id) {
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
    @Override
    public long countVehicles(){
        return repository.count();
    }

}
