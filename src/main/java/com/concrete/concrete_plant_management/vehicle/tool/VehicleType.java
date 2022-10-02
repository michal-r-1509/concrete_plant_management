package com.concrete.concrete_plant_management.vehicle.tool;

import lombok.Getter;

@Getter
public enum VehicleType {

    MIXER(1),
    MIXER_PUMP(2),
    PUMP(3);

    private final int value;

    VehicleType(final int value) {
        this.value = value;
    }
}
