package com.concrete.concrete_plant_management.vehicle.service;

import com.concrete.concrete_plant_management.domain.VehicleSchedule;
import com.concrete.concrete_plant_management.vehicle.repository.VehicleScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VehicleScheduleServiceImpl implements VehicleScheduleService{
    private final VehicleScheduleRepository repository;

    @Override
    public void saveSchedule(final VehicleSchedule schedule) {
        repository.save(schedule);
    }
}
