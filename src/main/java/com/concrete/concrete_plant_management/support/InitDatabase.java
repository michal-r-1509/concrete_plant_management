package com.concrete.concrete_plant_management.support;

import com.concrete.concrete_plant_management.client.service.ClientService;
import com.concrete.concrete_plant_management.domain.Client;
import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.domain.Vehicle;
import com.concrete.concrete_plant_management.domain.VehicleSchedule;
import com.concrete.concrete_plant_management.order.service.OrderService;
import com.concrete.concrete_plant_management.vehicle.dto.VehicleRequestDTO;
import com.concrete.concrete_plant_management.vehicle.service.VehicleScheduleService;
import com.concrete.concrete_plant_management.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
//@Profile({"local"})
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "local")
public class InitDatabase {

    private final VehicleService vehicleService;
    private final VehicleScheduleService vehicleScheduleService;
    private final ClientService clientService;
    private final OrderService orderService;


    @EventListener
//    @Order(Ordered.LOWEST_PRECEDENCE)
    public void onStartup(ApplicationReadyEvent event) {
        initVehicles();
        initSchedules();
        initClients();
        initOrders();

        log.info("INITIALIZED DATABASE WITH DUMMY ENTITIES");
    }

    private void initSchedules() {
        ScheduleFactory scheduleFactory = new ScheduleFactory();
        List<VehicleSchedule> schedule = scheduleFactory.getSchedules();
        List<Vehicle> vehicles = vehicleService.readAllVehicles();
        for (int i = 0; i < vehicles.size(); i++) {
            schedule.get(i * 2).setVehicle(vehicles.get(i));
            schedule.get((i * 2) + 1).setVehicle(vehicles.get(i));
            vehicleScheduleService.saveSchedule(schedule.get(i * 2));
            vehicleScheduleService.saveSchedule(schedule.get((i * 2) + 1));
        }
    }

    private void initVehicles() {
        VehicleFactory vehicleFactory = new VehicleFactory();
        List<VehicleRequestDTO> vehicles = vehicleFactory.getVehicles();
        vehicles.forEach(vehicle -> vehicleService.saveVehicle(vehicle));
    }

    private void initClients(){
        ClientFactory clientFactory = new ClientFactory();
        List<Client> clients = clientFactory.getClients();
        clients.forEach(client -> clientService.saveClient(client));
    }
    private void initOrders(){
        OrderFactory orderFactory = new OrderFactory();
        List<Order> orders =  orderFactory.getOrders();
        Random random = new Random();
        for (Order order : orders) {
            order.setClient(clientService.getClient(random.nextLong(1, 6)));
        }
        orders.forEach(order -> orderService.saveOrder(order));
    }
}
