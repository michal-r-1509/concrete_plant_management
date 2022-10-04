package com.concrete.concrete_plant_management.order.repository;

import com.concrete.concrete_plant_management.domain.Order;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryMethods {
    Order save(Order entity);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    List<Order> findAll(Sort sort);
    boolean existsById(Long id);
    void deleteById(Long id);
    boolean existsOrderByClient_Id(Long id);
}
