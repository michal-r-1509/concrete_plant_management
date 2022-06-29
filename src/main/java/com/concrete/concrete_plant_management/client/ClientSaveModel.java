package com.concrete.concrete_plant_management.client;

import com.concrete.concrete_plant_management.PostCode;

public class ClientSaveModel {

    public Client clientModelValidating(final Client toSave) {
        PostCode postCode = new PostCode(toSave.getPostCode());
        toSave.setPostCode(postCode.toString());

        toSave.setName(firstCharToUpperCase(toSave.getName()));
        toSave.setStreetAndNo(firstCharToUpperCase(toSave.getStreetAndNo()));
        toSave.setCity(firstCharToUpperCase(toSave.getCity()));

        return toSave;
    }

    String firstCharToUpperCase(String word){
        char firstChar = word.charAt(0);
        return (Character.toString(firstChar).toUpperCase().concat(word.substring(1)));
    }
}
