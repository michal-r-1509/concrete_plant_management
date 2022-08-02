package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.vehicle.Vehicle;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/order_batches")
class OrderBatchController {

    private final OrderBatchService orderBatchService;

    public OrderBatchController(final OrderBatchService orderBatchService) {
        this.orderBatchService = orderBatchService;
    }

    @PostMapping
    ResponseEntity <List<OrderBatch>> saveOrderBatch(@RequestBody @Valid List<OrderBatchWriteModel> toSaveList){
        List<OrderBatch> resultList =  orderBatchService.saveOrderBatch(toSaveList);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultList);
    }

    @Transactional
    @PatchMapping("/{id}")
    ResponseEntity<?> inverseOrderBatchStatus(@PathVariable int id){
        if(!orderBatchService.inverseStatus(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    ResponseEntity <OrderBatchReadModel> readOrderBatch(@PathVariable int id){
        OrderBatchReadModel orderBatch = orderBatchService.readOrderBatch(id);
        if (orderBatch == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(orderBatch);
        }
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity <List<OrderBatch>> readAllOrderBatches(){
        return ResponseEntity.ok(orderBatchService.readAllOrderBatches());
    }

    @GetMapping
    ResponseEntity <List<OrderBatch>> readAllOrderBatches(Sort sort){
        return ResponseEntity.ok(orderBatchService.readAllOrderBatches(sort));
    }

    @GetMapping("/search/{order_id}")
    ResponseEntity <List<OrderBatch>> readAllOrderBatchesByOrderId(@PathVariable int order_id){
        List<OrderBatch> resultList = orderBatchService.readAllOrderBatchesByOrderId(order_id);
        return ResponseEntity.ok(resultList);
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
