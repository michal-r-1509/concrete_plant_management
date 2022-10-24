package com.concrete.concrete_plant_management.order.batch.controller;

import com.concrete.concrete_plant_management.order.batch.dto.BatchDnPrintDTO;
import com.concrete.concrete_plant_management.order.batch.dto.BatchResponseDTO;
import com.concrete.concrete_plant_management.order.batch.service.BatchService;
import com.concrete.concrete_plant_management.order.event.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/batches")
public class BatchController {

    private final BatchService batchService;
    private final ApplicationEventPublisher publisher;

    @PatchMapping("/{id}")
    ResponseEntity<Void> inverseBatchStatus(@PathVariable Long id){
        OrderStatus orderStatus = batchService.inverseStatus(id);
        publisher.publishEvent(orderStatus);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    ResponseEntity <BatchResponseDTO> readBatch(@PathVariable Long id){
        BatchResponseDTO batch = batchService.readBatch(id);
        if (batch == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(batch);
        }
    }

    @GetMapping("/{id}/print")
    ResponseEntity <BatchDnPrintDTO> readBatchToDnPrint(@PathVariable Long id){
        BatchDnPrintDTO batch = batchService.readBatchToDnPrint(id);
        if (batch == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(batch);
        }
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity <List<BatchResponseDTO>> readAllBatches(){
        return ResponseEntity.ok(batchService.readAllBatches());
    }

    @GetMapping
    ResponseEntity <List<BatchResponseDTO>> readAllBatches(Sort sort){
        return ResponseEntity.ok(batchService.readAllBatches(sort));
    }

    @GetMapping("/search/{orderId}")
    ResponseEntity <List<BatchResponseDTO>> readAllBatchesByOrderId(@PathVariable Long orderId){
        List<BatchResponseDTO> resultList = batchService.readAllBatchesByOrderId(orderId);
        return ResponseEntity.ok(resultList);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBatch(@PathVariable Long id) {
        batchService.deleteBatch(id);
        return ResponseEntity.noContent().build();
    }
}
