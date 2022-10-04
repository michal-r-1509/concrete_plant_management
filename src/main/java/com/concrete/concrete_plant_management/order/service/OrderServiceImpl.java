package com.concrete.concrete_plant_management.order.service;

import com.concrete.concrete_plant_management.client.service.ClientService;
import com.concrete.concrete_plant_management.domain.Client;
import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order.repository.OrderRepositoryMethods;
import com.concrete.concrete_plant_management.order.dto.OrderRequestDTO;
import com.concrete.concrete_plant_management.order.dto.OrderResponseDTO;
import com.concrete.concrete_plant_management.order.tool.OrderMapper;
import com.concrete.concrete_plant_management.order_batch.OrderBatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepositoryMethods repository;
    private final OrderMapper mapper;
    private final OrderBatchService orderBatchService;
    private final ClientService clientService;

    public OrderServiceImpl(final OrderRepositoryMethods repository, final OrderMapper mapper,
                            final OrderBatchService orderBatchService, final ClientService clientService) {
        this.repository = repository;
        this.mapper = mapper;
        this.orderBatchService = orderBatchService;
        this.clientService = clientService;
    }

    @Override
    public OrderResponseDTO saveOrder(final OrderRequestDTO toSave) {
        Client client = clientService.getClient(toSave.getClientId());
        Order order = mapper.newOrderDataValidating(toSave, client);
        repository.save(order);
        log.info("created order with id: {}", order.getId());
        return mapper.toResponse(order);
    }

    public OrderResponseDTO saveOrder(final Order toSave) {
        repository.save(toSave);
        log.info("created order with id: {}", toSave.getId());
        return mapper.toResponse(toSave);
    }

    @Override
    public OrderResponseDTO updateOrder(final Long id, final OrderRequestDTO toSave) {
        Order existOrder = repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("order", id));
        Client client = clientService.getClient(toSave.getClientId());
        mapper.existOrderDataValidating(toSave, client, existOrder);
        repository.save(existOrder);
        log.info("updated order with id: {}", existOrder.getId());
        return mapper.toResponse(existOrder);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Order::getTime))
                .sorted(Comparator.comparing(Order::getDate))
                .map(order -> mapper.toResponse(order))
                .collect(Collectors.toList());
    }

    @Override
    public Order getOrder(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ElementNotFoundException("order", id));
    }

    @Override
    public List<OrderResponseDTO> getAllOrders(final Sort sort) {
        return repository.findAll(sort).stream()
                .map(order -> mapper.toResponse(order))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByState(final boolean status, final Sort sort) {
        return getAllOrders(sort).stream()
                .filter(order -> order.isDone() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(final Long id) {
        if (repository.existsById(id)) {
            orderBatchService.readAllOrderBatchesByOrderId(id)
                    .forEach(orderBatch -> orderBatchService.deleteOrderBatch(orderBatch.getId()));
            repository.deleteById(id);
        } else {
            throw new ElementNotFoundException("order", id);
        }
    }
}
