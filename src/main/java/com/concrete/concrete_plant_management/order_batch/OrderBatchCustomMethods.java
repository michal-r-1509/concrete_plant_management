package com.concrete.concrete_plant_management.order_batch;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface OrderBatchCustomMethods {
    List<OrderBatch> findAll();
    List<OrderBatch> findAll(Sort sort);
    OrderBatch save(OrderBatch entity);
    Optional<OrderBatch> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
    boolean existsOrderBatchByVehicle_Id(Long id);
    List<OrderBatch> findAllOrderBatchesByOrder_Id(Long id);
    @Query(nativeQuery = true, value = "select sum (amount) from order_batches where order_id=:orderId")
    double countOrderBatchesAmountByOrderId(@Param("orderId") Long orderId);
    boolean existsOrderBatchByOrder_Id(Long orderId);
}
