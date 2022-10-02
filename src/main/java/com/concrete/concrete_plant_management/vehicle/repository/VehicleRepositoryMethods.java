package com.concrete.concrete_plant_management.vehicle.repository;

import com.concrete.concrete_plant_management.domain.Vehicle;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface VehicleRepositoryMethods {
    List<Vehicle> findAll();
    List<Vehicle> findAll(Sort sort);
    Vehicle save(Vehicle entity);
    Optional<Vehicle> findById(Integer integer);
    boolean existsById(Integer integer);
    void deleteById(Integer integer);
    boolean existsByRegNo(String regNo);
    long count();
}
