package com.concrete.concrete_plant_management.domain;

import com.concrete.concrete_plant_management.vehicle.tool.VehicleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle extends BaseEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private String regNo;
    private double capacity;
    private double pumpLength;

    /*@JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<VehicleSchedule> schedule = new ArrayList<>();*/

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Schedule> schedule = new ArrayList<>();
}
