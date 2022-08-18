package com.concrete.concrete_plant_management.client;

import com.concrete.concrete_plant_management.PostCode;

class ClientDataValidation {

    public Client clientModelValidating(final Client toSave) {
        PostCode postCode = new PostCode(toSave.getPostCode());
        toSave.setPostCode(postCode.toString());

        toSave.setName(firstCharToUpperCase(toSave.getName()));
        toSave.setStreetAndNo(firstCharToUpperCase(toSave.getStreetAndNo()));
        toSave.setCity(firstCharToUpperCase(toSave.getCity()));

        return toSave;
    }

    String firstCharToUpperCase(String word){
        if (word.isBlank() || word.isEmpty()){
            return "";
        }
        String result = word.trim();
        char firstChar = result.charAt(0);
        return (Character.toString(firstChar).toUpperCase().concat(result.substring(1)));
    }
}
