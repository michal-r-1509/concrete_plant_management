package com.concrete.concrete_plant_management.order;

import com.concrete.concrete_plant_management.client.Client;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
//
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(LocalDate.of(2022,7,22),
                LocalTime.of(10,30,0),
                50, "C30/37", "długa 12",
                "teren grzaski", true, false,
                new Client(3)));
        orderList.add(new Order(LocalDate.of(2022,8,1),
                LocalTime.of(9,0,0),
                13, "C16/20", "przy drodze nr 43, krzepice",
                "dodatkowe rynny", false, true,
                new Client(5)));
        orderList.add(new Order(LocalDate.of(2022,7,19),
                LocalTime.of(10,0,0),
                65, "C20/25", "obok tesco, czewa",
                "", false, false,
                new Client(1)));
        for (Order element : orderList) {
            orderService.saveOrder(element);
        }
//
    }

    @PostMapping
    ResponseEntity<Order> saveOrder(@RequestBody @Valid Order toSave){
        Order result = orderService.saveOrder(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Order> updateOrder(@PathVariable int id, @RequestBody @Valid Order toUpdate){
        Order updated = orderService.updateOrder(id, toUpdate);
        if (updated == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updated);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> inverseStatus(@PathVariable int id){
        if(!orderService.inverseStatus(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity<List<Order>> readOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping
    ResponseEntity<List<Order>> readOrders(Sort sort){
        return ResponseEntity.ok(orderService.getAllOrders(sort));
    }

    @GetMapping("/{id}")
    ResponseEntity<Order> readOrder(@PathVariable int id){
        Order order = orderService.getOrder(id);
        if (order == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search")
    ResponseEntity<List<Order>> readOrders(
    @RequestParam(required = false, defaultValue = "false") boolean status, Sort sort){
        return ResponseEntity.ok(orderService.getAllOrdersByState(status, sort));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOrder(@PathVariable int id){
        if (!orderService.deleteOrder(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}