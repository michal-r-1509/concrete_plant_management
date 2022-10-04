package com.concrete.concrete_plant_management.order.controller;

import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.order.service.OrderServiceImpl;
import com.concrete.concrete_plant_management.order.dto.OrderRequestDTO;
import com.concrete.concrete_plant_management.order.dto.OrderResponseDTO;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
class OrderController {
    private final OrderServiceImpl orderService;

    public OrderController(final OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<OrderResponseDTO> saveOrder(@RequestBody @Valid OrderRequestDTO toSave) {
        OrderResponseDTO result = orderService.saveOrder(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequestDTO toUpdate) {
        OrderResponseDTO updated = orderService.updateOrder(id, toUpdate);
        return ResponseEntity.ok().body(updated);
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
    ResponseEntity<Order> readOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping("/search")
    ResponseEntity<List<OrderResponseDTO>> readOrders(
            @RequestParam(required = false, defaultValue = "false") boolean status, Sort sort) {
        return ResponseEntity.ok(orderService.getAllOrdersByState(status, sort));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}