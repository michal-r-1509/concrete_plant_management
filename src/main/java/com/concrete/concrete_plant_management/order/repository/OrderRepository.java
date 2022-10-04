package com.concrete.concrete_plant_management.order.repository;

import com.concrete.concrete_plant_management.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OrderRepository extends JpaRepository<Order, Integer>, OrderRepositoryMethods {
}
