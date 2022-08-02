package com.concrete.concrete_plant_management.order_batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface OrderBatchRepository extends JpaRepository<OrderBatch, Integer> {

    boolean existsOrderBatchByVehicle_Id(int id);
    List<OrderBatch> findAllOrderBatchesByOrder_Id(int id);
}
