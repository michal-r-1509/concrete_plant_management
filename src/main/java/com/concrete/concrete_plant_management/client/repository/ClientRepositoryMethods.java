package com.concrete.concrete_plant_management.client.repository;

import com.concrete.concrete_plant_management.domain.Client;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ClientRepositoryMethods {
    List<Client> findAll();
    List<Client> findAll(Sort sort);
    Client save(Client entity);
    Optional<Client> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
    boolean existsClientByTaxpayerIdentNo(Long nip);
    boolean existsClientByName(String name);
}
