package com.concrete.concrete_plant_management.client.tool;

import com.concrete.concrete_plant_management.domain.Client;
import com.concrete.concrete_plant_management.exceptions.TaxpayerIdentityNumberException;

public class ClientDataValidation {

    public Client clientDataValidating(final Client toSave) {
        String postCode = new PostCodeParser(toSave.getPostCode()).toString();
        return Client.builder()
                .name(toSave.getName())
                .streetAndNo(toSave.getStreetAndNo())
                .postCode(postCode)
                .city(toSave.getCity())
                .type(clientType(toSave.getTaxpayerIdentNo()))
                .taxpayerIdentNo(toSave.getTaxpayerIdentNo())
                .build();
    }

    private ClientType clientType(final Long taxpayerIdentNo){
        if (taxpayerIdentNo == 0L){
            return ClientType.INDIVIDUAL;
        }else if (taxpayerIdentNo >= 1000000000L && taxpayerIdentNo <= 9999999999L){
            return ClientType.BUSINESS;
        }else {
            throw new TaxpayerIdentityNumberException();
        }
    }
}
