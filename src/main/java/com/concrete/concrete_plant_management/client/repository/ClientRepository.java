package com.concrete.concrete_plant_management.client.repository;

import com.concrete.concrete_plant_management.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ClientRepository extends JpaRepository<Client, Integer>, ClientRepositoryMethods {

}
