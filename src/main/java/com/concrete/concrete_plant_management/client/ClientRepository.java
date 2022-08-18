package com.concrete.concrete_plant_management.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ClientRepository extends JpaRepository<Client, Integer>, ClientCustomMethods {

}
