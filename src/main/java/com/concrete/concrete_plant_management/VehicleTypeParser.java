package com.concrete.concrete_plant_management;

import com.concrete.concrete_plant_management.vehicle.tool.VehicleType;

public class VehicleTypeParser {

    public String vehicleTypeToString(VehicleType type){
        return switch (type.getValue()) {
            case 1 -> "gruszka";
            case 2 -> "pompo-gruszka";
            case 3 -> "pompa";
            default -> throw new IllegalArgumentException("bad vehicle type");
        };
    }
}
