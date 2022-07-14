package com.concrete.concrete_plant_management.order;

import com.concrete.concrete_plant_management.client.Client;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ApplicationEventPublisher eventPublisher;

    public OrderController(final OrderService orderService,
                           final ApplicationEventPublisher eventPublisher) {
        this.orderService = orderService;
        this.eventPublisher = eventPublisher;
//
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(LocalDate.of(2022,7,22),
                LocalTime.of(10,30,0),
                50, "C30/37", "długa 12", "teren grzaski", true,
                new Client(3)));
        orderList.add(new Order(LocalDate.of(2022,8,1),
                LocalTime.of(9,0,0),
                13, "C16/20", "przy drodze nr 43, krzepice", "dodatkowe rynny", false,
                new Client(5)));
        orderList.add(new Order(LocalDate.of(2022,7,19),
                LocalTime.of(10,0,0),
                65, "C20/25", "obok tesco, czewa", "", false,
                new Client(1)));
        for (Order element : orderList) {
            orderService.saveOrder(element);
        }
//
    }

    @PostMapping
    ResponseEntity<Order> saveOrder(@RequestBody @Valid Order toSave){
        System.out.println(toSave.getDate() + " " + toSave.getTime() + " " + toSave.getAmount());
        Order result = orderService.saveOrder(toSave);
        System.out.println(result.getId() + " " + result.isStatus());
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping
    ResponseEntity<List<Order>> readOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    ResponseEntity<Order> readOrder(@PathVariable int id){
        Order order = orderService.getOrder(id);
        if (order == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> inverseStatus(@PathVariable int id){
        if(!orderService.inverseStatus(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOrder(@PathVariable int id){
        if (!orderService.deleteOrder(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
