package com.concrete.concrete_plant_management.vehicle;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

interface VehicleCustomMethods {
    List<Vehicle> findAll();
    List<Vehicle> findAll(Sort sort);
    Vehicle save(Vehicle entity);
    Optional<Vehicle> findById(Integer integer);
    boolean existsById(Integer integer);
    void deleteById(Integer integer);
    boolean existsByRegNo(String regNo);
}
