package com.concrete.concrete_plant_management.archive;

import org.springframework.data.domain.Sort;

import java.util.List;

interface ArchiveCustomMethods {
    ArchivedOrderBatch save(ArchivedOrderBatch entity);
    List<ArchivedOrderBatch> findAll();
    List<ArchivedOrderBatch> findAll(Sort sort);
}
