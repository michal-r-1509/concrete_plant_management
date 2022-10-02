package com.concrete.concrete_plant_management.support;

import com.concrete.concrete_plant_management.domain.VehicleSchedule;
import com.concrete.concrete_plant_management.vehicle.service.VehicleScheduleService;
import com.concrete.concrete_plant_management.vehicle.service.VehicleScheduleServiceImpl;
import com.concrete.concrete_plant_management.vehicle.service.VehicleService;
import com.concrete.concrete_plant_management.vehicle.service.VehicleServiceImpl;
import com.concrete.concrete_plant_management.vehicle.dto.VehicleRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
//@Profile({"local"})
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "local")
public class InitDatabase {

    private final VehicleService vehicleService;
    private final VehicleScheduleService vehicleScheduleService;

    @EventListener
    @Order(Ordered.LOWEST_PRECEDENCE)
    public void onStartup(ApplicationReadyEvent event) {
        initVehicles();
        initSchedules();

        log.info("INITIALIZING DATABASE WITH DUMMY ENTITIES");
    }

    private void initSchedules() {
        ScheduleFactory scheduleFactory = new ScheduleFactory();
        List<VehicleSchedule> schedule = scheduleFactory.getSchedules();
        long vehiclesAmount = vehicleService.countVehicles();
        for (int i = 0; i < vehiclesAmount; i++) {
            schedule.get(i * 2).setVehicle(vehicleService.readVehicle(i + 1));
            schedule.get((i * 2) + 1).setVehicle(vehicleService.readVehicle(i + 1));
            vehicleScheduleService.saveSchedule(schedule.get(i * 2));
            vehicleScheduleService.saveSchedule(schedule.get((i * 2) + 1));
        }
    }

    private void initVehicles() {
        VehicleFactory vehicleFactory = new VehicleFactory();
        List<VehicleRequestDTO> vehicles = vehicleFactory.getVehicles();
        vehicles.forEach(vehicle -> vehicleService.saveVehicle(vehicle));
    }
}
