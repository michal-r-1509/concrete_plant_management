package com.concrete.concrete_plant_management.client;

import com.concrete.concrete_plant_management.exceptions.ElementConflictException;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order.OrderGlobalDao;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ClientService {

    private final ClientCustomMethods repository;
    private final OrderGlobalDao orderGlobalDao;

    public ClientService(final ClientCustomMethods repository,
                         final OrderGlobalDao orderGlobalDao) {
        this.repository = repository;
        this.orderGlobalDao = orderGlobalDao;
    }

    Client saveClient(Client toSave) {
        if (repository.existsClientByNip(toSave.getNip())){
            throw new ElementConflictException("client with nip number", String.valueOf(toSave.getNip()));
        }
        ClientDataValidation clientDataValidation = new ClientDataValidation();
        Client result = clientDataValidation.clientModelValidating(toSave);
        return repository.save(result);
    }

    Client getClient(int id){
        return repository.findById(id).orElseThrow(() -> new ElementNotFoundException("client", id));
    }

    List<Client> getAllClients(){
        return repository.findAll();
    }

    Client updateClient(final int id, final Client toUpdate) {
        if (repository.existsById(id)) {
            ClientDataValidation clientDataValidation = new ClientDataValidation();
            Client result = clientDataValidation.clientModelValidating(toUpdate);
            repository.save(toUpdate);
            return result;
        }
        throw new ElementNotFoundException("client", id);
    }

    public void deleteClient(final int id) {
        if (repository.existsById(id)){
            if (orderGlobalDao.existsOrderByClientId(id)){
                throw new ElementConflictException("client in active order");
            }else{
                repository.deleteById(id);
            }
        }else{
            throw new ElementNotFoundException("client", id);
        }
    }

    public List<Client> getAllClients(final Sort sort) {
        return repository.findAll(sort);
    }
}
