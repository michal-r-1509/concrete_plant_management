package com.concrete.concrete_plant_management.order.schedule;

import com.concrete.concrete_plant_management.domain.Schedule;
import com.concrete.concrete_plant_management.vehicle.repository.VehicleRepositoryMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
    private final VehicleRepositoryMethods vehicleRepository;

    @Override
    public void saveSchedule(final Schedule schedule) {
        repository.save(schedule);
    }

    @Override
    public void saveSchedule(final Schedule schedule, final Long vehicleId) {
        schedule.setVehicle(vehicleRepository.findById(vehicleId).get());
        repository.save(schedule);
    }

    @Override
    public List<Schedule> getScheduleByVehicleIdAndDate(final Long vehicleId, final LocalDate date) {
        return repository.findAllByVehicleIdAndDate(vehicleId, date);
    }
}
