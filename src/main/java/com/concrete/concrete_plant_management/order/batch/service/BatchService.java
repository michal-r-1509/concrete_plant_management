package com.concrete.concrete_plant_management.order.batch.service;

import com.concrete.concrete_plant_management.order.batch.dto.BatchDnPrintDTO;
import com.concrete.concrete_plant_management.order.batch.dto.BatchResponseDTO;
import com.concrete.concrete_plant_management.order.event.OrderStatus;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BatchService {
    OrderStatus inverseStatus(final Long id);
    BatchResponseDTO readBatch(final Long id);
    BatchDnPrintDTO readBatchToDnPrint(final Long id);
    List<BatchResponseDTO> readAllBatches();
    List<BatchResponseDTO> readAllBatches(final Sort sort);
    List<BatchResponseDTO> readAllBatchesByOrderId(final Long orderId);
    void deleteBatch(final Long id);
}
