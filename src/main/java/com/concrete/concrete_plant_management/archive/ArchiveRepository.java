package com.concrete.concrete_plant_management.archive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ArchiveRepository extends JpaRepository<ArchivedOrderBatch, String>, ArchiveCustomMethods {

}