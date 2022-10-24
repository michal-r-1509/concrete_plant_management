package com.concrete.concrete_plant_management.order.batch.service;

import com.concrete.concrete_plant_management.domain.Batch;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order.batch.dto.BatchDnPrintDTO;
import com.concrete.concrete_plant_management.order.batch.dto.BatchResponseDTO;
import com.concrete.concrete_plant_management.order.batch.repository.BatchRepository;
import com.concrete.concrete_plant_management.order.batch.tool.BatchMapper;
import com.concrete.concrete_plant_management.order.event.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BatchServiceImpl implements BatchService {

    private final BatchRepository repository;
    private final BatchMapper mapper;

    @Override
    public OrderStatus inverseStatus(final Long id) {
        Batch batch = repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("orderBatch", id));
        batch.setDone(!batch.isDone());
        repository.save(batch);
        log.info("changed status for batch with id {} to {}", id, batch.isDone());

        Long orderId = batch.getOrder().getId();
        double orderAmount = batch.getOrder().getAmount();
        double batchesAmount = 0;
        List<Batch> batches = repository.findAllByOrderId(orderId);

        boolean batchesStatus = batches.stream().allMatch(Batch::isDone);
        if (batchesStatus){
            for (Batch entity : batches) {
                batchesAmount += entity.getAmount();
            }
        }

        return OrderStatus.changingOrderStatus(batchesStatus && orderAmount == batchesAmount, orderId);
    }

    @Override
    public BatchResponseDTO readBatch(final Long id) {
        Batch batch = repository.findById(id).orElseThrow(() -> new ElementNotFoundException("batch", id));
        List<Batch> batchList = new ArrayList<>();
        batchList.add(batch);
        log.info("reading batch with id: {}", id);
        return mapper.toBatchResponse(batchList).get(0);
    }

    @Override
    public BatchDnPrintDTO readBatchToDnPrint(final Long id) {
        Batch batch = repository.findById(id).orElseThrow(() -> new ElementNotFoundException("batch", id));
        log.info("reading batch with id: {}", id);
        return mapper.toBatchDnPrintResponse(batch);
    }

    @Override
    public List<BatchResponseDTO> readAllBatches() {
        List<Batch> batches = repository.findAll();
        log.info("reading all batches");
        return mapper.toBatchResponse(batches);
    }

    @Override
    public List<BatchResponseDTO> readAllBatches(final Sort sort) {
        List<Batch> batches = repository.findAll(sort);
        log.info("reading all batches with sorting");
        return mapper.toBatchResponse(batches);
    }

    @Override
    public List<BatchResponseDTO> readAllBatchesByOrderId(final Long orderId) {
        List<Batch> batches = repository.findAllByOrderId(orderId);
        log.info("reading all batches by order id");
        return mapper.toBatchResponse(batches);
    }

    @Override
    public void deleteBatch(final Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("batch with id: {} deleted", id);
        } else {
            throw new ElementNotFoundException("batch", id);
        }
    }
}
