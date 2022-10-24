package com.concrete.concrete_plant_management.order.service;

import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.order.batch.dto.BatchRequestDTO;
import com.concrete.concrete_plant_management.order.batch.dto.BatchResponseDTO;
import com.concrete.concrete_plant_management.order.dto.OrderRequestDTO;
import com.concrete.concrete_plant_management.order.dto.OrderResponseDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderService {
    OrderResponseDTO saveOrder(final OrderRequestDTO toSave);
    OrderResponseDTO saveOrder(final Order toSave);
    List<BatchResponseDTO> saveOrUpdateBatches(final Long id, final List<BatchRequestDTO> toSave);
    OrderResponseDTO updateOrder(final Long id, final OrderRequestDTO toSave);
    void changeOrderStatus(boolean status, final Long id);
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrder(final Long id);
    List<OrderResponseDTO> getAllOrders(final Sort sort);
    List<OrderResponseDTO> getAllOrdersByState(final boolean status, final Sort sort);
    void deleteOrder(final Long id);
}
