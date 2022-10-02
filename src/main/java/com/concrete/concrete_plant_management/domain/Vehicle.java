package com.concrete.concrete_plant_management.domain;

import com.concrete.concrete_plant_management.vehicle.tool.VehicleType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    @NotBlank
    @Size(min = 6, max = 8)
    private String regNo;
    private double capacity;
    private double pumpLength;

    @Transient
    @OneToMany(mappedBy = "vehicle", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<VehicleSchedule> schedule = new ArrayList<>();
}
