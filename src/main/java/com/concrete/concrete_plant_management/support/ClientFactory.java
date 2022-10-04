package com.concrete.concrete_plant_management.support;

import com.concrete.concrete_plant_management.domain.Client;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClientFactory {
    private List<Client> clients = new ArrayList<>();

    public ClientFactory() {
        clients.add(Client.builder()
                .name("Cezary Cezary")
                .streetAndNo("Krakowska 33")
                .postCode("31554")
                .city("Wieliczka")
                .taxpayerIdentNo(0L)
                .build());
        clients.add(Client.builder()
                .name("Jurek Ogorek")
                .streetAndNo("Lipowa 2")
                .postCode("55-999")
                .city("Zabierzow")
                .taxpayerIdentNo(0L)
                .build());
        clients.add(Client.builder()
                .name("Budowlanka sp. z o.o.")
                .streetAndNo("Opolska 1")
                .postCode("22-212")
                .city("Krakow")
                .taxpayerIdentNo(5552223366L)
                .build());
        clients.add(Client.builder()
                .name("Fachowcy.pl")
                .streetAndNo("Kasztanowa 21")
                .postCode("55111")
                .city("Zielonki")
                .taxpayerIdentNo(9988891133L)
                .build());
        clients.add(Client.builder()
                .name("Anyfachowcy")
                .streetAndNo("Zepsuta 13")
                .postCode("13-666")
                .city("Sosnowiec")
                .taxpayerIdentNo(1366671371L)
                .build());
    }
}
