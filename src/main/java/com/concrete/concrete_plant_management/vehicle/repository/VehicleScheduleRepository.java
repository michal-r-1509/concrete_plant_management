package com.concrete.concrete_plant_management.vehicle.repository;

import com.concrete.concrete_plant_management.domain.VehicleSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleScheduleRepository extends JpaRepository<VehicleSchedule, Long> {
}
