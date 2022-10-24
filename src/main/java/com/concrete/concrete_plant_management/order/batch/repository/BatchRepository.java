package com.concrete.concrete_plant_management.order.batch.repository;

import com.concrete.concrete_plant_management.domain.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    List<Batch> findAllByOrderId(Long orderId);
}
