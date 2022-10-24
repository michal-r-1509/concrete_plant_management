package com.concrete.concrete_plant_management.archive.tool;

import com.concrete.concrete_plant_management.domain.ArchivedBatch;
import com.concrete.concrete_plant_management.domain.Batch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArchiveMapper {

    private final DnParser dnParser;
    private final TaxpayerIdentNoParser taxpayerIdentNoParser;
    private final VehicleTypeParser vehicleTypeParser;

    public ArchivedBatch toArchive(final Batch batch) {
        return ArchivedBatch.builder()
                .dnNo(dnParser.toString(batch.getId()))
                .date(batch.getOrder().getDate())
                .time(batch.getSchedule().getStartTime())
                .siteAddress(batch.getOrder().getSiteAddress())
                .clientName(batch.getOrder().getClient().getName())
                .clientAddress(batch.getOrder().getClient().getStreetAndNo())
                .clientPostCode(batch.getOrder().getClient().getPostCode())
                .clientCity(batch.getOrder().getClient().getCity())
                .clientNip(taxpayerIdentNoParser.toString(batch.getOrder().getClient().getTaxpayerIdentNo()))
                .vehicleType(vehicleTypeParser.toString(batch.getSchedule().getVehicle().getType()))
                .vehicleName(batch.getSchedule().getVehicle().getName())
                .vehicleRegNo(batch.getSchedule().getVehicle().getRegNo())
                .concreteClass(batch.getOrder().getConcreteClass())
                .amount(batch.getAmount())
                .build();
    }
}
