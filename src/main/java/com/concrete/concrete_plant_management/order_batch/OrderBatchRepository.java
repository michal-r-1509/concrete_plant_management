package com.concrete.concrete_plant_management.order_batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OrderBatchRepository extends JpaRepository<OrderBatch, Integer>, OrderBatchCustomMethods {

}
