package com.concrete.concrete_plant_management.order;

import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order_batch.OrderBatchService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
class OrderService{
    private final OrderCustomMethods repository;
    private final OrderBatchService orderBatchService;

    public OrderService(final OrderCustomMethods repository,
                        final OrderBatchService orderBatchService) {
        this.repository = repository;
        this.orderBatchService = orderBatchService;
    }

    public Order saveOrder(Order toSave) {
        OrderDataValidation dataValidation = new OrderDataValidation();
        dataValidation.orderDataValidating(toSave);
        return repository.save(toSave);
    }

    public Order updateOrder(final int id, final Order toUpdate) {
        if (repository.existsById(id)) {
            orderBatchService.updateOrderBatches(toUpdate);
            return repository.save(toUpdate);
        }
        throw new ElementNotFoundException("order", id);
    }

    public List<Order> getAllOrders() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Order::getTime))
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public Order getOrder(final int id) {
        return repository.findById(id).orElseThrow(() -> new ElementNotFoundException("order", id));
    }

    public List<Order> getAllOrders(final Sort sort) {
        return repository.findAll(sort);
    }

    public List<Order> getAllOrdersByState(final boolean status, final Sort sort) {
        return getAllOrders(sort).stream()
                .filter(order -> order.isDone() == status)
                .collect(Collectors.toList());
    }

    public void deleteOrder(final int id) {
        if (repository.existsById(id)) {
            orderBatchService.readAllOrderBatchesByOrderId(id)
                    .forEach(orderBatch -> orderBatchService.deleteOrderBatch(orderBatch.getId()));
            repository.deleteById(id);
        } else {
            throw new ElementNotFoundException("order", id);
        }
    }
}
