package com.concrete.concrete_plant_management.client;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

interface ClientCustomMethods {
    List<Client> findAll();
    List<Client> findAll(Sort sort);
    Client save(Client entity);
    Optional<Client> findById(Integer integer);
    boolean existsById(Integer integer);
    void deleteById(Integer integer);
    boolean existsClientByNip(long nip);
}
