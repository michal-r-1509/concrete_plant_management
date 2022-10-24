package com.concrete.concrete_plant_management.archive.service;

import com.concrete.concrete_plant_management.archive.repository.ArchiveRepository;
import com.concrete.concrete_plant_management.archive.tool.ArchiveMapper;
import com.concrete.concrete_plant_management.domain.ArchivedBatch;
import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.exceptions.IllegalOperationException;
import com.concrete.concrete_plant_management.order.repository.OrderRepositoryMethods;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArchiveService {

    private final ArchiveRepository repository;
    private final ArchiveMapper mapper;
    private final OrderRepositoryMethods orderRepository;

    public void saveToArchive(final Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ElementNotFoundException("order", orderId));
        if (order.isDone()) {
            List<ArchivedBatch> archivedBatches = order.getBatches().stream()
                    .map(mapper::toArchive)
                    .collect(Collectors.toList());
            repository.saveAll(archivedBatches);
            log.info("batches for order with id {} archived", orderId);
        } else {
            throw new IllegalOperationException(orderId);
        }
    }

    public List<ArchivedBatch> readAllArchivedBatches() {
        log.info("reading all archived batches with sorting");
        return repository.findAll().stream()
                .sorted(Comparator.comparing(ArchivedBatch::getTime))
                .sorted(Comparator.comparing(ArchivedBatch::getDate))
                .collect(Collectors.toList());
    }

    public List<ArchivedBatch> readAllArchivedBatches(final Sort sort) {
        log.info("reading all archived batches");
        return repository.findAll(sort);
    }
}
