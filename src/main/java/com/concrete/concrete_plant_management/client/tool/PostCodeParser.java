package com.concrete.concrete_plant_management.client.tool;

import org.springframework.stereotype.Component;

@Component
public record PostCodeParser() {

    public String toString(String postCode) {
        if (postCode.length() < 5 || postCode.length() > 6){
            return "";
        }
        else if(postCode.length() == 6 && '-' == postCode.charAt(2)){
            return postCode;
        }
        else if (postCode.length() == 6){
            return "";
        }
        else {
            return postCode.substring(0, 2) + "-" + postCode.substring(2);
        }
    }
}
