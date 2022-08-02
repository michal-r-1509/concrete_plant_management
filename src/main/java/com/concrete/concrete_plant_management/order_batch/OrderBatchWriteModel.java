package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.order.Order;
import com.concrete.concrete_plant_management.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
class OrderBatchWriteModel {
    @Setter
    private int id;
    private int batchId;
    private float amount;
    private LocalTime time;
    private boolean toDelete;
    private Order order;
    private Vehicle vehicle;
}
