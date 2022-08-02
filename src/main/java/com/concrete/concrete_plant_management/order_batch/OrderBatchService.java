package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.order.Order;
import com.concrete.concrete_plant_management.order.OrderService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderBatchService {

    private final OrderBatchRepository repository;
    private final OrderService orderService;

    public OrderBatchService(final OrderBatchRepository repository, @Lazy final OrderService orderService) {
        this.repository = repository;
        this.orderService = orderService;
    }

    public List<OrderBatch> saveOrderBatch(final List<OrderBatchWriteModel> toSaveList) {
        toSaveList.stream()
                .filter(OrderBatchWriteModel::isToDelete)
                .forEach(orderBatchWriteModel -> deleteOrderBatch(orderBatchWriteModel.getBatchId()));

        return toSaveList.stream()
                .filter(orderBatchWriteModel -> orderBatchWriteModel.getBatchId() == 0)
                .filter(orderBatchWriteModel -> !orderBatchWriteModel.isToDelete())
                .map(this::toOrderBatch)
                .map(repository::save)
                .collect(Collectors.toList());
    }

    private OrderBatch toOrderBatch(final OrderBatchWriteModel toSave) {
        return new OrderBatch(toSave.getAmount(), toSave.getTime(), false,
                toSave.getOrder(), toSave.getVehicle());
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
        OrderBatch orderBatch = repository.findById(id).orElse(null);
        if (orderBatch != null) {
            return new OrderBatchReadModel(orderBatch);
        }
        return null;
    }

    public void updateOrderBatches(Order order) {
        repository.findAllOrderBatchesByOrder_Id(order.getId())
                .forEach(orderBatch -> orderBatch.setOrder(order));
    }

    public boolean deleteOrderBatch(final int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean inverseStatus(final int id) {
        if (repository.existsById(id)) {
            OrderBatch orderBatch = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("orderBatch not found"));
            orderBatch.setStatus(!orderBatch.isStatus());
            repository.save(orderBatch);
            boolean orderStatus = repository.findAllOrderBatchesByOrder_Id(orderBatch.getOrder().getId()).stream()
                    .allMatch(OrderBatch::isStatus);
            Order order = orderService.getOrder(orderBatch.getOrder().getId());
            order.setStatus(orderStatus);
            orderService.updateOrder(order.getId(), order);
            return true;
        } else {
            return false;
        }
    }

    public void setStatusToAllOrderBatches(int orderId, boolean status) {
        repository.findAllOrderBatchesByOrder_Id(orderId)
                .forEach(orderBatch -> orderBatch.setStatus(status));
    }

    public boolean existsOrderBatchByVehicleId(final int id) {
        return repository.existsOrderBatchByVehicle_Id(id);
    }
}
