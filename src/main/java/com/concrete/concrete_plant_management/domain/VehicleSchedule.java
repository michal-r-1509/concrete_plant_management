package com.concrete.concrete_plant_management.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle_schedules")
public class VehicleSchedule extends BaseEntity{
    private LocalDate date;
    @NotNull
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
