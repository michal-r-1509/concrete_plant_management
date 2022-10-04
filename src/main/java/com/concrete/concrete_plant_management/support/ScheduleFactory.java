package com.concrete.concrete_plant_management.support;

import com.concrete.concrete_plant_management.domain.VehicleSchedule;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ScheduleFactory {
    private List<VehicleSchedule> schedules = new ArrayList<>();

    public ScheduleFactory() {
        schedules.add(VehicleSchedule.builder()
                .date(LocalDate.of(2022, 11, 11))
                .startTime(LocalTime.of(10, 30, 00))
                .endTime(LocalTime.of(11, 30, 00))
                .build());
        schedules.add(VehicleSchedule.builder()
                .date(LocalDate.of(2022, 11, 11))
                .startTime(LocalTime.of(11, 40, 00))
                .endTime(LocalTime.of(12, 40, 00))
                .build());
        schedules.add(VehicleSchedule.builder()
                .date(LocalDate.of(2022, 10, 10))
                .startTime(LocalTime.of(10, 30, 00))
                .endTime(LocalTime.of(11, 30, 00))
                .build());
        schedules.add(VehicleSchedule.builder()
                .date(LocalDate.of(2022, 10, 10))
                .startTime(LocalTime.of(11, 40, 00))
                .endTime(LocalTime.of(12, 40, 00))
                .build());
        schedules.add(VehicleSchedule.builder()
                .date(LocalDate.of(2022, 9, 9))
                .startTime(LocalTime.of(10, 30, 00))
                .endTime(LocalTime.of(11, 30, 00))
                .build());
        schedules.add(VehicleSchedule.builder()
                .date(LocalDate.of(2022, 9, 9))
                .startTime(LocalTime.of(11, 40, 00))
                .endTime(LocalTime.of(12, 40, 00))
                .build());
    }
}
