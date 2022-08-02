package com.concrete.concrete_plant_management.vehicle;

class VehicleDataValidation {

    private Vehicle vehicleToValidate;

    void vehicleDataValidation(Vehicle vehicleToValidate) {
        this.vehicleToValidate = vehicleToValidate;
        regNoValidating();
        typeValidating();
        amountsValidating();
    }

    private void regNoValidating() {
        vehicleToValidate.setRegNo(vehicleToValidate.getRegNo().toUpperCase());
    }

    private void typeValidating(){
        if (vehicleToValidate.getType().equalsIgnoreCase("gruszka")){
            vehicleToValidate.setPumpLength(0f);
        }else if (vehicleToValidate.getType().equalsIgnoreCase("pompa")){
            vehicleToValidate.setCapacity(0f);
        }
    }

    private void amountsValidating() {
        if (vehicleToValidate.getCapacity() > 30.0f) {
            vehicleToValidate.setCapacity(30.0f);
        }
        if (vehicleToValidate.getPumpLength() > 100.0f) {
            vehicleToValidate.setPumpLength(100.0f);
        }
    }
}
