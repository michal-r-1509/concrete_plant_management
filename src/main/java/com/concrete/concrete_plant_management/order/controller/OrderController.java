package com.concrete.concrete_plant_management.order.controller;

import com.concrete.concrete_plant_management.archive.service.ArchiveService;
import com.concrete.concrete_plant_management.order.batch.dto.BatchRequestDTO;
import com.concrete.concrete_plant_management.order.batch.dto.BatchResponseDTO;
import com.concrete.concrete_plant_management.order.dto.OrderRequestDTO;
import com.concrete.concrete_plant_management.order.dto.OrderResponseDTO;
import com.concrete.concrete_plant_management.order.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
class OrderController {

    private final OrderServiceImpl orderService;
    private final ArchiveService archiveService;

    @PostMapping
    ResponseEntity<OrderResponseDTO> saveOrder(@RequestBody @Valid OrderRequestDTO toSave) {
        OrderResponseDTO result = orderService.saveOrder(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}/batches")
    ResponseEntity<List<BatchResponseDTO>> updateOrderBatch(@PathVariable Long id,
                                                            @RequestBody @Valid List<BatchRequestDTO> toSave) {
        List<BatchResponseDTO> batches = orderService.saveOrUpdateBatches(id, toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(batches);
    }

    @PutMapping("/{id}")
    ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequestDTO toUpdate) {
        OrderResponseDTO updated = orderService.updateOrder(id, toUpdate);
        return ResponseEntity.ok().body(updated);
    }

    @PatchMapping("/{id}/archive")
    ResponseEntity<Void> createArchiveOrder(@PathVariable(name = "id") Long orderId){
        archiveService.saveToArchive(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity<List<OrderResponseDTO>> readOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping
    ResponseEntity<List<OrderResponseDTO>> readOrders(Sort sort) {
        return ResponseEntity.ok(orderService.getAllOrders(sort));
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderResponseDTO> readOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping("/search")
    ResponseEntity<List<OrderResponseDTO>> readOrders(
            @RequestParam(required = false, defaultValue = "false") boolean status, Sort sort) {
        return ResponseEntity.ok(orderService.getAllOrdersByState(status, sort));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}