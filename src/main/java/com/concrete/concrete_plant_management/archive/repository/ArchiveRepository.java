package com.concrete.concrete_plant_management.archive.repository;

import com.concrete.concrete_plant_management.domain.ArchivedBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveRepository extends JpaRepository<ArchivedBatch, Long> {

}