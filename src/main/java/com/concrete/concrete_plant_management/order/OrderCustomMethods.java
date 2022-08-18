package com.concrete.concrete_plant_management.order;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

interface OrderCustomMethods {
    Order save(Order entity);
    Optional<Order> findById(Integer integer);
    List<Order> findAll();
    List<Order> findAll(Sort sort);
    boolean existsById(Integer integer);
    void deleteById(Integer integer);
    boolean existsOrderByClient_Id(int id);
}
