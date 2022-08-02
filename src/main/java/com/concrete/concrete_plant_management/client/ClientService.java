package com.concrete.concrete_plant_management.client;

import com.concrete.concrete_plant_management.order.OrderService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;
    private final OrderService orderService;

    public ClientService(final ClientRepository repository,
                         final OrderService orderService) {
        this.repository = repository;
        this.orderService = orderService;
    }

    Client saveClient(Client toSave) {
        if (repository.existsClientByNip(toSave.getNip())){
            return null;
        }
        ClientDataValidation clientDataValidation = new ClientDataValidation();
        Client result = clientDataValidation.clientModelValidating(toSave);
        return repository.save(result);
    }

    Client getClient(int id){
        return repository.findById(id).orElse(null);
    }

    List<Client> getAllClients(){
        return repository.findAll();
    }

    Client updateClient(final int id, final Client toUpdate) {
        if (!repository.existsById(id)) {
            return null;
        }
        ClientDataValidation clientDataValidation = new ClientDataValidation();
        Client result = clientDataValidation.clientModelValidating(toUpdate);
        repository.save(toUpdate);
        return result;
    }

    public boolean deleteClient(final int id) {
        if (repository.existsById(id) && !orderService.existsOrderByClientId(id)){
            repository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public List<Client> getAllClients(final Sort sort) {
        return repository.findAll(sort);
    }
}
