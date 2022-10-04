package com.concrete.concrete_plant_management.order.event;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
public class OrderStatus {

    private final boolean status;
    private final Long orderId;

    public OrderStatus(final boolean status, final Long orderId) {
        this.status = status;
        this.orderId = orderId;
    }

    public static OrderStatus changingOrderStatus(final boolean status, final Long orderId){
        return new OrderStatus(status, orderId);
    }
}
