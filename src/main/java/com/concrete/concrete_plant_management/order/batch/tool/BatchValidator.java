package com.concrete.concrete_plant_management.order.batch.tool;

import com.concrete.concrete_plant_management.domain.Schedule;
import com.concrete.concrete_plant_management.order.batch.repository.BatchRepository;
import com.concrete.concrete_plant_management.order.schedule.ScheduleService;
import com.concrete.concrete_plant_management.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BatchValidator {
    private final BatchRepository repository;
    private final VehicleService vehicleService;
    private final ScheduleService scheduleService;

    public boolean vehicleExistCheck(final Long vehicleId){
        return vehicleService.existById(vehicleId);
    }

    public boolean scheduleCheck(final Long vehicleId, final LocalDate date,
                                 final LocalTime startTime, final int duration) {
        LocalTime endTime = startTime.plusMinutes(duration);
        List<Schedule> vehicleSchedules = scheduleService.getScheduleByVehicleIdAndDate(vehicleId, date);
        boolean exist = vehicleSchedules.stream().anyMatch(schedule -> {
            if ((startTime.isBefore(schedule.getEndTime()) && startTime.isAfter(schedule.getStartTime())) ||
                    endTime.isBefore(schedule.getEndTime()) && endTime.isAfter(schedule.getStartTime())){
                return true;
            }
            return false;
        });
        return exist;
    }

    public void batchRemover(final boolean toDelete, final Long id) {
        if (toDelete && repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
