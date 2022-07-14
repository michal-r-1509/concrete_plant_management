package com.concrete.concrete_plant_management.vehicle;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(final VehicleService vehicleService) {
        this.vehicleService = vehicleService;

        //
        List<Vehicle> vhList = new ArrayList<>();
        vhList.add(new Vehicle("Iveco Astra COIME", "Pompo-gruszka", "WW 54123", "", 9f, 28f));
        vhList.add(new Vehicle("Mercedes Benz Schwing", "Pompa", "WE 34545", "", 9f, 36f));
        vhList.add(new Vehicle("Mercedes Benz Actros", "Gruszka", "SW 2434", "", 10f, 28f));
        vhList.add(new Vehicle("MAN TGS", "Gruszka", "DD 24442", "uszkodzony", 10f, 28f));
        vhList.add(new Vehicle("Volvo FH12", "Gruszka", "DW 99541", "", 9f, 28f));
        vhList.add(new Vehicle("Volvo FH13", "Gruszka", "DW 99541", "", 9f, 28f));
        vhList.add(new Vehicle("Volvo FH14", "Gruszka", "DW 99541", "", 9f, 28f));
        vhList.add(new Vehicle("Volvo FH15", "Gruszka", "DW 99541", "", 9f, 28f));
        vhList.add(new Vehicle("Volvo FH16", "Gruszka", "DW 99541", "", 9f, 28f));
        vhList.add(new Vehicle("Volvo FH17", "Gruszka", "DW 99541", "", 9f, 28f));
        vhList.add(new Vehicle("Volvo FH18", "Gruszka", "DW 99541", "", 9f, 28f));
        vhList.add(new Vehicle("Volvo FH19", "Gruszka", "DW 99541", "", 9f, 28f));
        for (Vehicle element : vhList) {
            vehicleService.saveVehicle(element);
        }
        //
    }

    @PostMapping
    ResponseEntity<Vehicle> saveVehicle(@RequestBody @Valid Vehicle toSave) {
        Vehicle result = vehicleService.saveVehicle(toSave);
        return ResponseEntity.created(URI.create("/vehicles/" + result.getId())).body(result);
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
            return ResponseEntity.ok(vehicleService.readVehicle(id));
        }
    }

    @GetMapping
    ResponseEntity<List<Vehicle>> readAllVehicles() {
        return ResponseEntity.ok(vehicleService.readAllVehicles());
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
