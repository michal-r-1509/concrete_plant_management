package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.vehicle.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/order_batches")
public class OrderBatchController {

    private final OrderBatchService orderBatchService;

    public OrderBatchController(final OrderBatchService orderBatchService) {
        this.orderBatchService = orderBatchService;
    }

    @PostMapping
    ResponseEntity <Set<OrderBatch>> saveOrderBatch(@RequestBody @Valid Set<OrderBatch> toSaveList){
        Set<OrderBatch> resultList =  orderBatchService.saveOrderBatch(toSaveList);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultList);
    }

    @GetMapping
    ResponseEntity <List<OrderBatch>> readAllOrderBatches(){
        return ResponseEntity.ok(orderBatchService.readAllOrderBatches());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Vehicle> deleteVehicle(@PathVariable int id) {
        boolean isDeleted = orderBatchService.deleteOrderBatch(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
