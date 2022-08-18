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
    Optional<OrderBatch> findById(Integer integer);
    boolean existsById(Integer integer);
    void deleteById(Integer integer);
    boolean existsOrderBatchByVehicle_Id(int id);
    List<OrderBatch> findAllOrderBatchesByOrder_Id(int id);
    @Query(nativeQuery = true, value = "select sum (amount) from order_batches where order_id=:orderId")
    double countOrderBatchesAmountByOrderId(@Param("orderId") Integer orderId);
    boolean existsOrderBatchByOrder_Id(int orderId);
}
