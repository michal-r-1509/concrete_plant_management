package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.domain.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
class OrderBatchWriteModel {
    @Setter
    private Long id;
    private Long batchId;
    private double amount;
    private LocalTime time;
    private boolean toDelete;
    private Order order;
    private Vehicle vehicle;
}
