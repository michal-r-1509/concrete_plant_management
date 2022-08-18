package com.concrete.concrete_plant_management.vehicle;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/vehicles")
class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(final VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    ResponseEntity<Vehicle> saveVehicle(@RequestBody @Valid Vehicle toSave) {
        Vehicle result = vehicleService.saveVehicle(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Vehicle> patchVehicle(@PathVariable int id, @RequestBody Vehicle toUpdate) {
        Vehicle result = vehicleService.updateVehicle(id, toUpdate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    ResponseEntity<Vehicle> readVehicle(@PathVariable int id) {
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
    ResponseEntity<Vehicle> deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
