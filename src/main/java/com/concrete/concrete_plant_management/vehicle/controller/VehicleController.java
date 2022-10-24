package com.concrete.concrete_plant_management.vehicle.controller;

import com.concrete.concrete_plant_management.domain.Vehicle;
import com.concrete.concrete_plant_management.vehicle.service.VehicleService;
import com.concrete.concrete_plant_management.vehicle.dto.VehicleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vehicles")
class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    ResponseEntity<Vehicle> saveVehicle(@RequestBody @Valid VehicleRequestDTO toSave) {
        Vehicle result = vehicleService.saveVehicle(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Vehicle> patchVehicle(@PathVariable Long id, @RequestBody VehicleRequestDTO toUpdate) {
        Vehicle result = vehicleService.updateVehicle(id, toUpdate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    ResponseEntity<Vehicle> readVehicle(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.readVehicle(id));
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity<List<Vehicle>> readAllVehicles() {
        return ResponseEntity.ok(vehicleService.readAllVehicles());
    }

    @GetMapping
    ResponseEntity<List<Vehicle>> readAllVehicles(Sort sort) {
        return ResponseEntity.ok(vehicleService.readAllVehicles(sort));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
