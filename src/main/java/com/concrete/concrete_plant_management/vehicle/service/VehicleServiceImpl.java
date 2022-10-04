package com.concrete.concrete_plant_management.vehicle.service;

import com.concrete.concrete_plant_management.domain.Vehicle;
import com.concrete.concrete_plant_management.exceptions.ElementConflictException;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order_batch.OrderBatchService;
import com.concrete.concrete_plant_management.vehicle.dto.VehicleRequestDTO;
import com.concrete.concrete_plant_management.vehicle.repository.VehicleRepositoryMethods;
import com.concrete.concrete_plant_management.vehicle.tool.VehicleDataValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
        Vehicle vehicle = new VehicleDataValidation().vehicleValidation(toSave);
        if (repository.existsByRegNo(vehicle.getRegNo())) {
            throw new ElementConflictException("vehicle", "registry number", vehicle.getRegNo());
        }
        log.info("created vehicle with registry number: {}", vehicle.getRegNo());
        return repository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(final Long id, final VehicleRequestDTO toUpdate) {
        Vehicle vehicle = new VehicleDataValidation().vehicleValidation(toUpdate);
        if (!repository.existsById(id)) {
            throw new ElementNotFoundException("vehicle", id);
        }
        if (repository.existsByRegNo(vehicle.getRegNo())) {
            throw new ElementConflictException("vehicle", "registry number", vehicle.getRegNo());
        }
        log.info("updated vehicle with id: {}", id);
        return repository.save(vehicle);
    }

    @Override
    public Vehicle readVehicle(final Long id) {
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
    public void deleteVehicle(final Long id) {
        if (repository.existsById(id)){
            if (orderBatchService.existsOrderBatchByVehicleId(id)){
                throw new ElementConflictException("vehicle in orderBatch");
            }else{
                repository.deleteById(id);
                log.info("vehicle with id: {} deleted", id);
            }
        }else{
            throw new ElementNotFoundException("vehicle", id);
        }
    }
}
