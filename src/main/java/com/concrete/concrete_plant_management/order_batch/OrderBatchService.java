package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.order.event.OrderStatus;
import com.concrete.concrete_plant_management.domain.Vehicle;
import com.concrete.concrete_plant_management.vehicle.service.VehicleGlobalDao;
import com.concrete.concrete_plant_management.vehicle.tool.VehicleType;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderBatchService {

    private final OrderBatchCustomMethods repository;
    private final VehicleGlobalDao vehicleGlobalDao;

    public OrderBatchService(final OrderBatchCustomMethods repository,
                             final VehicleGlobalDao vehicleGlobalDao) {
        this.repository = repository;
        this.vehicleGlobalDao = vehicleGlobalDao;
    }

    public List<OrderBatch> saveOrderBatch(final List<OrderBatchWriteModel> toSaveList) {
        toSaveList.stream()
                .filter(OrderBatchWriteModel::isToDelete)
                .forEach(orderBatch -> {
                    if (repository.existsById(orderBatch.getBatchId())){
                        deleteOrderBatch(orderBatch.getBatchId());
                    }
                });

        return toSaveList.stream()
                .filter(orderBatch -> orderBatch.getBatchId() == 0)
                .filter(orderBatch -> !orderBatch.isToDelete())
                .filter(orderBatch -> {
                    Vehicle vehicle = vehicleGlobalDao.getVehicle(orderBatch.getVehicle().getId());
                    return !(orderBatch.getAmount() == 0.0 && vehicle.getType().equals(VehicleType.MIXER));
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

    public List<OrderBatch> readAllOrderBatchesByOrderId(final Long order_id) {
        return repository.findAll().stream()
                .filter(orderBatch -> orderBatch.getOrder().getId() == order_id)
                .sorted(Comparator.comparing(OrderBatch::getTime))
                .collect(Collectors.toList());
    }

    public List<OrderBatch> readAllOrderBatches(final Sort sort) {
        return repository.findAll(sort);
    }

    public OrderBatchReadModel readOrderBatch(final Long id) {
        return new OrderBatchReadModel(repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("orderBatch", id)));
    }

    public void updateOrderBatches(Order order) {
        repository.findAllOrderBatchesByOrder_Id(order.getId())
                .forEach(orderBatch -> orderBatch.setOrder(order));
    }

    public void deleteOrderBatch(final Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ElementNotFoundException("orderBatch", id);
        }
    }

    public OrderStatus inverseStatus(final Long id) {
        OrderBatch orderBatch = repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("orderBatch", id));
        orderBatch.setDone(!orderBatch.isDone());
        repository.save(orderBatch);

        Long orderId = orderBatch.getOrder().getId();
        double orderAmount = orderBatch.getOrder().getAmount();

        boolean allBatchesStatus = repository.findAllOrderBatchesByOrder_Id(orderBatch.getOrder().getId()).stream()
                .allMatch(OrderBatch::isDone);
        double allBatchesAmount = repository.countOrderBatchesAmountByOrderId(orderId);

        return OrderStatus.changingOrderStatus(allBatchesStatus && orderAmount == allBatchesAmount, orderId);
    }

    public boolean existsOrderBatchByVehicleId(final Long id) {
        return repository.existsOrderBatchByVehicle_Id(id);
    }
}
