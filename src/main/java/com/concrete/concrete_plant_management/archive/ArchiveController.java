package com.concrete.concrete_plant_management.archive;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/archives")
class ArchiveController {

    private final ArchiveService service;

    public ArchiveController(final ArchiveService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<?> createArchiveEntity(@RequestBody ArchiveWriteModel orderId){
        service.saveToArchive(orderId.getOrderId());
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity<List<ArchivedOrderBatch>> readAllArchivedOrderBatches(){
        return ResponseEntity.ok(service.readArchive());
    }

    @GetMapping
    ResponseEntity<List<ArchivedOrderBatch>> readAllArchivedOrderBatches(Sort sort){
        return ResponseEntity.ok(service.readArchive(sort));
    }
}
