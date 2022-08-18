package com.concrete.concrete_plant_management.order;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<Order> saveOrder(@RequestBody @Valid Order toSave) {
        Order result = orderService.saveOrder(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Order> updateOrder(@PathVariable int id, @RequestBody @Valid Order toUpdate) {
        Order updated = orderService.updateOrder(id, toUpdate);
        return ResponseEntity.ok().body(updated);
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity<List<Order>> readOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping
    ResponseEntity<List<Order>> readOrders(Sort sort) {
        return ResponseEntity.ok(orderService.getAllOrders(sort));
    }

    @GetMapping("/{id}")
    ResponseEntity<Order> readOrder(@PathVariable int id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping("/search")
    ResponseEntity<List<Order>> readOrders(
            @RequestParam(required = false, defaultValue = "false") boolean status, Sort sort) {
        return ResponseEntity.ok(orderService.getAllOrdersByState(status, sort));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}