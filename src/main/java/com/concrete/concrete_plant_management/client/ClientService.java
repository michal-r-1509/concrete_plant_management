package com.concrete.concrete_plant_management.client;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(final ClientRepository repository) {
        this.repository = repository;
    }

    Client saveClient(Client toSave) {
        ClientSaveModel clientSaveModel = new ClientSaveModel();
        Client result = clientSaveModel.clientModelValidating(toSave);
        return repository.save(result);
    }

    Client getClient(int id){
        return repository.findById(id).orElse(null);
    }

    List<Client> getAllClients(){
        return repository.findAll();
    }
}
