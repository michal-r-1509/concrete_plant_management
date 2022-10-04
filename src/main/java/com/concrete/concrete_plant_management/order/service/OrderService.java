package com.concrete.concrete_plant_management.order.service;

import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.order.dto.OrderRequestDTO;
import com.concrete.concrete_plant_management.order.dto.OrderResponseDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderService {
    OrderResponseDTO saveOrder(final OrderRequestDTO toSave);
    OrderResponseDTO saveOrder(final Order toSave);
    OrderResponseDTO updateOrder(final Long id, final OrderRequestDTO toSave);
    List<OrderResponseDTO> getAllOrders();
    Order getOrder(final Long id);
    List<OrderResponseDTO> getAllOrders(final Sort sort);
    List<OrderResponseDTO> getAllOrdersByState(final boolean status, final Sort sort);
    void deleteOrder(final Long id);
}
