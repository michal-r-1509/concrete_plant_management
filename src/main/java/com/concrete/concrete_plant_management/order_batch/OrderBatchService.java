package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.exceptions.OrderBatchNotFoundException;
import com.concrete.concrete_plant_management.order.Order;
import com.concrete.concrete_plant_management.order.OrderService;
import com.concrete.concrete_plant_management.vehicle.Vehicle;
import com.concrete.concrete_plant_management.vehicle.VehicleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderBatchService {

    private final OrderBatchCustomMethods repository;
    private final OrderService orderService;
    private final VehicleService vehicleService;

    public OrderBatchService(final OrderBatchCustomMethods repository,
                             @Lazy final OrderService orderService,
                             @Lazy final VehicleService vehicleService) {
        this.repository = repository;
        this.orderService = orderService;
        this.vehicleService = vehicleService;
    }

    public List<OrderBatch> saveOrderBatch(final List<OrderBatchWriteModel> toSaveList) {
        toSaveList.stream()
                .filter(OrderBatchWriteModel::isToDelete)
                .forEach(orderBatch -> deleteOrderBatch(orderBatch.getBatchId()));

        return toSaveList.stream()
                .filter(orderBatch -> orderBatch.getBatchId() == 0)
                .filter(orderBatch -> !orderBatch.isToDelete())
                .filter(orderBatch -> {
                    Vehicle vehicle = vehicleService.readVehicle(orderBatch.getVehicle().getId());
                    return !(orderBatch.getAmount() == 0.0f &&
                            vehicle.getType().equalsIgnoreCase("gruszka"));
                })
                .map(this::toOrderBatch)
                .map(repository::save)
                .collect(Collectors.toList());
    }

    private OrderBatch toOrderBatch(final OrderBatchWriteModel toSave) {
        return new OrderBatch(toSave.getAmount(), toSave.getTime(), toSave.getOrder(), toSave.getVehicle());
    }

    public List<OrderBatch> readAllOrderBatches() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(OrderBatch::getTime))
                .sorted(Comparator.comparing(o -> o.getOrder().getDate()))
                .collect(Collectors.toList());
    }

    public List<OrderBatch> readAllOrderBatchesByOrderId(final int order_id) {
        return repository.findAll().stream()
                .filter(orderBatch -> orderBatch.getOrder().getId() == order_id)
                .sorted(Comparator.comparing(OrderBatch::getTime))
                .collect(Collectors.toList());
    }

    public List<OrderBatch> readAllOrderBatches(final Sort sort) {
        return repository.findAll(sort);
    }

    public OrderBatchReadModel readOrderBatch(final int id) {
        return new OrderBatchReadModel(repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("orderBatch", id)));
    }

    public void updateOrderBatches(Order order) {
        repository.findAllOrderBatchesByOrder_Id(order.getId())
                .forEach(orderBatch -> orderBatch.setOrder(order));
    }

    public void deleteOrderBatch(final int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ElementNotFoundException("orderBatch", id);
        }
    }

    public void inverseStatus(final int id) {
        OrderBatch orderBatch = repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("orderBatch", id));
        orderBatch.setStatus(!orderBatch.isStatus());
        repository.save(orderBatch);
        boolean orderStatus = repository.findAllOrderBatchesByOrder_Id(orderBatch.getOrder().getId()).stream()
                .allMatch(OrderBatch::isStatus);
        Order order = orderService.getOrder(orderBatch.getOrder().getId());
        order.setStatus(orderStatus);
        orderService.updateOrder(order.getId(), order);
    }

    public void setStatusToAllOrderBatches(int orderId, boolean status) {
        repository.findAllOrderBatchesByOrder_Id(orderId)
                .forEach(orderBatch -> orderBatch.setStatus(status));
    }

    public boolean existsOrderBatchByVehicleId(final int id) {
        return repository.existsOrderBatchByVehicle_Id(id);
    }
    public double orderBatchesAmountSumByOrderId(int id){
        if (!repository.existsOrderBatchByOrder_Id(id)) {
            throw new OrderBatchNotFoundException("orderBatches with order id ", id);
        }
        return repository.countOrderBatchesAmountByOrderId(id);
    }
}
