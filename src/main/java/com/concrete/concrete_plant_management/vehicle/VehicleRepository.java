package com.concrete.concrete_plant_management.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface VehicleRepository extends JpaRepository<Vehicle, Integer>, VehicleCustomMethods {

}