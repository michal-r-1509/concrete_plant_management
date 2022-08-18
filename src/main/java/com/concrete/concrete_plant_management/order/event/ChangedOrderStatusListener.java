package com.concrete.concrete_plant_management.order.event;

import com.concrete.concrete_plant_management.order.OrderGlobalDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
class ChangedOrderStatusListener {
    private final static Logger logger = LoggerFactory.getLogger(ChangedOrderStatusListener.class);

    private final OrderGlobalDao orderGlobalDao;

    public ChangedOrderStatusListener(final OrderGlobalDao orderGlobalDao) {
        this.orderGlobalDao = orderGlobalDao;
    }

    @Async
    @EventListener
    public void changedStatus(OrderStatus orderStatus){
        changingStatus(orderStatus);
    }

    private void changingStatus(final OrderStatus orderStatus) {
        orderGlobalDao.changeOrderStatus(orderStatus.isStatus(), orderStatus.getOrderId());
        logger.info("status of order with id " + orderStatus.getOrderId() + " is changed to " + orderStatus.isStatus());
    }
}
