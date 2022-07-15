package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.order_batch.OrderBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBatchRepository extends JpaRepository<OrderBatch, Integer> {
}
