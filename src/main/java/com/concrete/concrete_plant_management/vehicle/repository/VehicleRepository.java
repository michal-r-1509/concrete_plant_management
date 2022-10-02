package com.concrete.concrete_plant_management.vehicle.repository;

import com.concrete.concrete_plant_management.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface VehicleRepository extends JpaRepository<Vehicle, Integer>, VehicleRepositoryMethods {

}