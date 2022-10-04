package com.concrete.concrete_plant_management.order;

import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order.repository.OrderRepositoryMethods;
import org.springframework.stereotype.Component;

@Component
public class OrderGlobalDao {
    private final OrderRepositoryMethods repository;

    public OrderGlobalDao(final OrderRepositoryMethods repository) {
        this.repository = repository;
    }

    public void changeOrderStatus(boolean status, final Long id){
        Order order = repository.findById(id).orElseThrow(() -> new ElementNotFoundException("order", id));
        order.setDone(status);
        repository.save(order);
    }

    public boolean existsOrderByClientId(final Long id) {
        return repository.existsOrderByClient_Id(id);
    }
}
