package com.concrete.concrete_plant_management.vehicle;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vehicles")
class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(final VehicleService vehicleService) {
        this.vehicleService = vehicleService;

        //
        List<Vehicle> list = new ArrayList<>();
        list.add(new Vehicle("Iveco COIME", "Pompo-gruszka", "WW 54123", "", 9f, 28f));
        list.add(new Vehicle("Mercedes Schwing", "Pompa", "WE 34545", "", 9f, 36f));
        list.add(new Vehicle("Mercedes Actros", "Gruszka", "SW 2434", "", 10f, 28f));
        list.add(new Vehicle("MAN TGS", "Gruszka", "DD 24442", "uszkodzony", 10f, 28f));
        list.add(new Vehicle("Volvo FH12", "Gruszka", "DW 99541", "", 9f, 28f));
        list.forEach(vehicleService::saveVehicle);
        //
    }

    @PostMapping
    ResponseEntity<Vehicle> saveVehicle(@RequestBody @Valid Vehicle toSave) {
        Vehicle result = vehicleService.saveVehicle(toSave);
        if (result == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Vehicle> patchVehicle(@PathVariable int id, @RequestBody Vehicle toUpdate) {
        Vehicle result = vehicleService.vehicleUpdate(id, toUpdate);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Vehicle> readVehicle(@PathVariable int id) {
        Vehicle vehicle = vehicleService.readVehicle(id);
        if (vehicle == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(vehicle);
        }
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
        boolean isDeleted = vehicleService.deleteVehicle(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
