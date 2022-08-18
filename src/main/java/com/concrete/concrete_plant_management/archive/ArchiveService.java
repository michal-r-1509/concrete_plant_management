package com.concrete.concrete_plant_management.archive;

import com.concrete.concrete_plant_management.DnParser;
import com.concrete.concrete_plant_management.NipParser;
import com.concrete.concrete_plant_management.VehicleTypeParser;
import com.concrete.concrete_plant_management.order_batch.OrderBatch;
import com.concrete.concrete_plant_management.order_batch.OrderBatchService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
class ArchiveService {

    private final ArchiveCustomMethods repository;
    private final OrderBatchService orderBatchService;

    public ArchiveService(final ArchiveCustomMethods repository, final OrderBatchService orderBatchService) {
        this.repository = repository;
        this.orderBatchService = orderBatchService;
    }

    public void saveToArchive(final int orderId) {
        orderBatchService.readAllOrderBatchesByOrderId(orderId).stream()
                .map(this::toArchive)
                .forEach(repository::save);
    }

    private ArchivedOrderBatch toArchive(final OrderBatch orderBatch) {
        DnParser dnId = new DnParser(orderBatch.getId());
        NipParser nip = new NipParser(orderBatch.getOrder().getClient().getNip());
        VehicleTypeParser vehicleTypeParser = new VehicleTypeParser();
        String dnNo = dnId.toString();
        LocalDate date = orderBatch.getOrder().getDate();
        LocalTime time = orderBatch.getTime();
        String siteAddress = orderBatch.getOrder().getSiteAddress();
        String clientName = orderBatch.getOrder().getClient().getName();
        String clientAddress = orderBatch.getOrder().getClient().getStreetAndNo();
        String clientPostCode = orderBatch.getOrder().getClient().getPostCode();
        String clientCity = orderBatch.getOrder().getClient().getCity();
        String clientNip = nip.toString();
        String vehicleType = vehicleTypeParser.vehicleTypeToString(orderBatch.getVehicle().getType());
        String vehicleName = orderBatch.getVehicle().getName();
        String vehicleRegNo = orderBatch.getVehicle().getRegNo();
        String concreteClass = orderBatch.getOrder().getConcreteClass();
        double amount = orderBatch.getAmount();

        return new ArchivedOrderBatch(dnNo, date, time, siteAddress, clientName, clientAddress, clientPostCode,
                clientCity, clientNip, vehicleType, vehicleName, vehicleRegNo, concreteClass, amount);
    }

    public List<ArchivedOrderBatch> readArchive() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(ArchivedOrderBatch::getTime))
                .sorted(Comparator.comparing(ArchivedOrderBatch::getDate))
                .collect(Collectors.toList());
    }

    public List<ArchivedOrderBatch> readArchive(final Sort sort) {
        return repository.findAll(sort);
    }
}
