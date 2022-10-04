package com.concrete.concrete_plant_management.client.service;

import com.concrete.concrete_plant_management.client.tool.ClientDataValidation;
import com.concrete.concrete_plant_management.client.tool.ClientType;
import com.concrete.concrete_plant_management.client.repository.ClientRepositoryMethods;
import com.concrete.concrete_plant_management.domain.Client;
import com.concrete.concrete_plant_management.exceptions.ElementConflictException;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order.OrderGlobalDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepositoryMethods repository;
    private final OrderGlobalDao orderGlobalDao;

    public ClientServiceImpl(final ClientRepositoryMethods repository,
                             final OrderGlobalDao orderGlobalDao) {
        this.repository = repository;
        this.orderGlobalDao = orderGlobalDao;
    }

    @Override
    public Client saveClient(Client toSave) {
        ClientDataValidation clientDataValidation = new ClientDataValidation();
        Client client = clientDataValidation.clientDataValidating(toSave);
        clientBasicChecks(client);
        log.info("created client with name: {}, and type: {}", client.getName(), client.getType().toString());
        return repository.save(client);
    }

    @Override
    public Client updateClient(final Long id, final Client toUpdate) {
        ClientDataValidation clientDataValidation = new ClientDataValidation();
        Client client = clientDataValidation.clientDataValidating(toUpdate);

        if (!repository.existsById(id)) {
            throw new ElementNotFoundException("client", id);
        }

        clientBasicChecks(client);
        log.info("updated client with id: {}", client.getId());
        return repository.save(toUpdate);
    }

    @Override
    public Client getClient(Long id) {
        return repository.findById(id).orElseThrow(() -> new ElementNotFoundException("client", id));
    }

    @Override
    public List<Client> getAllClients() {
        return repository.findAll();
    }

    @Override
    public List<Client> getAllClients(final Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public void deleteClient(final Long id) {
        if (repository.existsById(id)) {
            if (orderGlobalDao.existsOrderByClientId(id)) {
                throw new ElementConflictException("client in active order");
            } else {
                repository.deleteById(id);
                log.info("deleted client with id: {}", id);
            }
        } else {
            throw new ElementNotFoundException("client", id);
        }
    }

    private void clientBasicChecks(Client client){
        if (client.getType().equals(ClientType.BUSINESS) &&
                repository.existsClientByTaxpayerIdentNo(client.getTaxpayerIdentNo())) {
            throw new ElementConflictException(
                    "client", "taxpayer identity number", String.valueOf(client.getTaxpayerIdentNo()));
        }
        if(repository.existsClientByName(client.getName())){
            throw new ElementConflictException("client", "name", client.getName());
        }
    }
}
