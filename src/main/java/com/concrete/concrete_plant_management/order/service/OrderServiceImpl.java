package com.concrete.concrete_plant_management.order.service;

import com.concrete.concrete_plant_management.client.service.ClientService;
import com.concrete.concrete_plant_management.domain.Batch;
import com.concrete.concrete_plant_management.domain.Client;
import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order.batch.dto.BatchRequestDTO;
import com.concrete.concrete_plant_management.order.batch.dto.BatchResponseDTO;
import com.concrete.concrete_plant_management.order.batch.repository.BatchRepository;
import com.concrete.concrete_plant_management.order.batch.tool.BatchMapper;
import com.concrete.concrete_plant_management.order.batch.tool.BatchValidator;
import com.concrete.concrete_plant_management.order.dto.OrderRequestDTO;
import com.concrete.concrete_plant_management.order.dto.OrderResponseDTO;
import com.concrete.concrete_plant_management.order.repository.OrderRepositoryMethods;
import com.concrete.concrete_plant_management.order.tool.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepositoryMethods repository;
    private final OrderMapper mapper;
    private final BatchMapper batchMapper;
    private final BatchRepository batchRepository;
    private final BatchValidator batchValidator;
    private final ClientService clientService;

    @Override
    public OrderResponseDTO saveOrder(final OrderRequestDTO toSave) {
        Client client = clientService.getClient(toSave.getClientId());
        Order order = mapper.newOrderDataValidating(toSave, client);
        repository.save(order);
        log.info("created order with id: {}", order.getId());
        return mapper.toResponse(order);
    }

    @Override
    public OrderResponseDTO saveOrder(final Order toSave) {
        repository.save(toSave);
        log.info("created order with id: {}", toSave.getId());
        return mapper.toResponse(toSave);
    }

    @Override
    public List<BatchResponseDTO> saveOrUpdateBatches(final Long id, final List<BatchRequestDTO> toSave) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("order", id));

        List<Batch> batches = toSave.stream()
                .filter(batchReqDTO -> batchValidator.vehicleExistCheck(batchReqDTO.getVehicleId()))
                .peek(batchReqDTO -> batchValidator.batchRemover(batchReqDTO.isToDelete(), batchReqDTO.getId()))
                .filter(batchReqDTO -> !batchValidator.scheduleCheck(
                        batchReqDTO.getVehicleId(),
                        order.getDate(),
                        batchReqDTO.getTime(),
                        batchReqDTO.getDuration())
                )
                .filter(batchReqDTO -> !batchReqDTO.isToDelete())
                .peek(batchReqDTO -> {
                    if (batchRepository.existsById(batchReqDTO.getId())) {
                        Batch batch = batchRepository.findById(batchReqDTO.getId()).get();
                        batchMapper.updateBatch(batch, batchReqDTO);
                        batchRepository.save(batch);
                    }
                })
                .filter(batchReqDTO -> !batchRepository.existsById(batchReqDTO.getId()))
                .map(batchReqDTO -> batchMapper.toBatch(batchReqDTO, order))
                .collect(Collectors.toList());

        batchRepository.saveAll(batches);
        List<Batch> byOrderBatches = batchRepository.findAllByOrderId(order.getId());
        return batchMapper.toBatchResponse(byOrderBatches);
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
    public void changeOrderStatus(boolean status, final Long id){
        Order order = repository.findById(id).orElseThrow(() -> new ElementNotFoundException("order", id));
        order.setDone(status);
        repository.save(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        log.info("reading all orders");
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Order::getTime))
                .sorted(Comparator.comparing(Order::getDate))
                .map(order -> mapper.toResponse(order))
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO getOrder(final Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new ElementNotFoundException("order", id));
        log.info("reading order with id: {}", id);
        return mapper.toResponse(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders(final Sort sort) {
        log.info("reading all orders with sorting");
        return repository.findAll(sort).stream()
                .map(order -> mapper.toResponse(order))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByState(final boolean status, final Sort sort) {
        log.info("reading all orders with sorting by status");
        return getAllOrders(sort).stream()
                .filter(order -> order.isDone() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(final Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("order with id: {} deleted", id);
        } else {
            throw new ElementNotFoundException("order", id);
        }
    }
}
