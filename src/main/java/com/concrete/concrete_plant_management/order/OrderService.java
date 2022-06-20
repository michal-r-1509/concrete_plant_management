package com.concrete.concrete_plant_management.order;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(final OrderRepository repository) {
        this.repository = repository;
    }

    public boolean inverseStatus(final int id) {
        if (repository.existsById(id)) {
            repository.findById(id)
                    .ifPresent(Order::inverseStatus);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteOrder(final int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            return false;
        }
        return true;
    }

    public Order saveOrder(Order orderToSave) {
        Order saved = repository.save(orderToSave);
        return saved;
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order getOrder(final int id) {
        if (repository.existsById(id)) {
            return repository.findById(id).get();
        } else {
            return null;
        }
    }
}
