package com.concrete.concrete_plant_management.order.schedule;

import com.concrete.concrete_plant_management.domain.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    void saveSchedule(final Schedule schedule);
    void saveSchedule(final Schedule schedule, final Long vehicleId);
    List<Schedule> getScheduleByVehicleIdAndDate(final Long vehicleId, final LocalDate date);
}
