package com.concrete.concrete_plant_management;

public record PostCode(String postCode) {

    @Override
    public String toString() {
        if (postCode.length() < 5 || postCode.length() > 6){
            return "bad post code format";
        }
        else if(postCode.length() == 6 && '-' == postCode.charAt(2)){
            return postCode;
        }
        else if (postCode.length() == 6){
            return "bad post code format";
        }
        else {
            return postCode.substring(0, 2) + "-" + postCode.substring(2);
        }
    }
}
