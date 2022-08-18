package com.concrete.concrete_plant_management;

public class VehicleTypeParser {
    public String vehicleTypeToString(int type){
        return switch (type) {
            case 1 -> "gruszka";
            case 2 -> "pompo-gruszka";
            case 3 -> "pompa";
            default -> throw new IllegalArgumentException("bad vehicle type");
        };
    }
}
