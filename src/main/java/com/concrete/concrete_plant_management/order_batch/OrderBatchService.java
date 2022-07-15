package com.concrete.concrete_plant_management.order_batch;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderBatchService {

    public final OrderBatchRepository repository;

    public OrderBatchService(final OrderBatchRepository repository) {
        this.repository = repository;
    }

    public Set<OrderBatch> saveOrderBatch(final Set<OrderBatch> toSaveList) {
        Set<OrderBatch> resultList = toSaveList.stream()
                .map(repository::save)
                .collect(Collectors.toSet());
        return resultList;
    }

    public List<OrderBatch> readAllOrderBatches() {
        List<OrderBatch> orderBatches = repository.findAll().stream()
                .sorted(Comparator.comparing(o -> o.getOrder().getDate()))
                .sorted(Comparator.comparing(OrderBatch::getTime))
                .collect(Collectors.toList());
        return orderBatches;
    }

    public boolean deleteOrderBatch(final int id) {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return true;
            } else {
                return false;
            }
    }
}
