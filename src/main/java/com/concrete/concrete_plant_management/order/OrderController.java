package com.concrete.concrete_plant_management.order;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;
    private final ApplicationEventPublisher eventPublisher;

    public OrderController(final OrderService service,
                           final ApplicationEventPublisher eventPublisher) {
        this.service = service;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    ResponseEntity<Order> saveOrder(@RequestBody @Valid Order toSave){
        System.out.println(toSave.getDate() + " " + toSave.getTime() + " " + toSave.getAmount());
        Order result = service.saveOrder(toSave);
        System.out.println(result.getId() + " " + result.isStatus());
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping
    ResponseEntity<List<Order>> readOrders(){
        return ResponseEntity.ok(service.getAllOrders());
    }

    @GetMapping("/{id}")
    ResponseEntity<Order> readOrder(@PathVariable int id){
        Order order = service.getOrder(id);
        if (order == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> inverseStatus(@PathVariable int id){
        if(!service.inverseStatus(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOrder(@PathVariable int id){
        if (!service.deleteOrder(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
