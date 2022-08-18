package com.concrete.concrete_plant_management.vehicle;

class VehicleDataValidation {

    private Vehicle vehicleToValidate;

    void vehicleDataValidation(Vehicle vehicleToValidate) {
        this.vehicleToValidate = vehicleToValidate;
        nameValidating();
        regNoValidating();
        typeValidating();
        amountsValidating();
    }

    private void nameValidating() {
        vehicleToValidate.setName(vehicleToValidate.getName().trim());
    }

    private void regNoValidating() {
        String result = vehicleToValidate.getRegNo().toUpperCase();
        vehicleToValidate.setRegNo(result.replaceAll("\\s+",""));
    }

private void typeValidating(){
    if (vehicleToValidate.getType() == 1){
        vehicleToValidate.setPumpLength(0f);
    }else if (vehicleToValidate.getType() == 3){
        vehicleToValidate.setCapacity(0f);
    }
}

    private void amountsValidating() {
        if (vehicleToValidate.getCapacity() > 30.0f) {
            vehicleToValidate.setCapacity(9.0f);
        }
        if (vehicleToValidate.getPumpLength() > 100.0f) {
            vehicleToValidate.setPumpLength(24.0f);
        }
    }
}
