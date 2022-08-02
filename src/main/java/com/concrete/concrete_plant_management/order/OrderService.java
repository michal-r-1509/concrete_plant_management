package com.concrete.concrete_plant_management.order;

import com.concrete.concrete_plant_management.order_batch.OrderBatchService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderBatchService orderBatchService;

    public OrderService(final OrderRepository repository,
                        final OrderBatchService orderBatchService) {
        this.repository = repository;
        this.orderBatchService = orderBatchService;
    }

    public Order saveOrder(Order toSave) {
        return repository.save(toSave);
    }

    public Order updateOrder(final int id, final Order toUpdate) {
        if (!repository.existsById(id)){
            return null;
        }
        orderBatchService.updateOrderBatches(toUpdate);
        return repository.save(toUpdate);
    }

    public List<Order> getAllOrders() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Order::getTime))
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

    public Order getOrder(final int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Order> getAllOrders(final Sort sort) {
        return repository.findAll(sort);
    }

    public List<Order> getAllOrdersByState(final boolean status, final Sort sort) {
        return getAllOrders(sort).stream()
                .filter(order -> order.isStatus() == status)
                .collect(Collectors.toList());
    }

    public boolean deleteOrder(final int id) {
        if (repository.existsById(id)) {
            orderBatchService.readAllOrderBatchesByOrderId(id)
                    .forEach(orderBatch -> orderBatchService.deleteOrderBatch(orderBatch.getId()));
            repository.deleteById(id);
        } else {
            return false;
        }
        return true;
    }

    public boolean inverseStatus(final int id) {
        if (repository.existsById(id)) {
            Order toUpdate = repository.findById(id).orElseThrow(() -> new RuntimeException("order not found"));
            toUpdate.setStatus(!toUpdate.isStatus());
            orderBatchService.setStatusToAllOrderBatches(id, toUpdate.isStatus());
            return true;
        } else {
            return false;
        }
    }

    public boolean existsOrderByClientId(final int id) {
        return repository.existsOrderByClient_Id(id);
    }
}
