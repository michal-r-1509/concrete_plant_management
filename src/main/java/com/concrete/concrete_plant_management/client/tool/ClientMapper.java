package com.concrete.concrete_plant_management.client.tool;

import com.concrete.concrete_plant_management.domain.Client;
import com.concrete.concrete_plant_management.exceptions.TaxpayerIdentityNumberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientMapper {

    private final PostCodeParser postCodeParser;

    public Client newClientValidating(final Client toSave) {
        return Client.builder()
                .name(toSave.getName())
                .streetAndNo(toSave.getStreetAndNo())
                .postCode(postCodeParser.toString(toSave.getPostCode()))
                .city(toSave.getCity())
                .taxpayerIdentNo(toSave.getTaxpayerIdentNo())
                .type(clientType(toSave.getTaxpayerIdentNo()))
                .build();
    }

    public void existClientValidating(final Client existClient, final Client toUpdate) {
        existClient.setName(toUpdate.getName());
        existClient.setStreetAndNo(toUpdate.getStreetAndNo());
        existClient.setPostCode(postCodeParser.toString(toUpdate.getPostCode()));
        existClient.setCity(toUpdate.getCity());
        existClient.setTaxpayerIdentNo(toUpdate.getTaxpayerIdentNo());
        existClient.setType(clientType(toUpdate.getTaxpayerIdentNo()));
    }

    public ClientType clientType(final Long taxpayerIdentNo){
        if (taxpayerIdentNo == 0L){
            return ClientType.INDIVIDUAL;
        }else if (taxpayerIdentNo >= 1000000000L && taxpayerIdentNo <= 9999999999L){
            return ClientType.BUSINESS;
        }else {
            throw new TaxpayerIdentityNumberException();
        }
    }
}
