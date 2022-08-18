package com.concrete.concrete_plant_management.order;

import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class OrderGlobalDao {
    private final OrderCustomMethods repository;

    public OrderGlobalDao(final OrderCustomMethods repository) {
        this.repository = repository;
    }

    public void changeOrderStatus(boolean status, final int id){
        Order order = repository.findById(id).orElseThrow(() -> new ElementNotFoundException("order", id));
        order.setDone(status);
        repository.save(order);
    }

    public boolean existsOrderByClientId(final int id) {
        return repository.existsOrderByClient_Id(id);
    }
}
