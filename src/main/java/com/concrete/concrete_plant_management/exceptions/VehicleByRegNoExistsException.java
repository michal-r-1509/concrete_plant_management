package com.concrete.concrete_plant_management.exceptions;

public class VehicleByRegNoExistsException extends RuntimeException{
    public VehicleByRegNoExistsException(final String regNo) {
        super(regNo);
    }
}
