package com.concrete.concrete_plant_management;

public record PostCode(String postCode) {

    @Override
    public String toString() {
        //TODO
        if (postCode.length() != 5){
            return "bad post code format";
        }else {
            return postCode.substring(0, 2) + "-" + postCode.substring(2);
        }
    }
}
