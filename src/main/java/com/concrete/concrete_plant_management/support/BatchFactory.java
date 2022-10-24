package com.concrete.concrete_plant_management.support;

import com.concrete.concrete_plant_management.order.batch.dto.BatchRequestDTO;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BatchFactory {
    private List<BatchRequestDTO> batches = new ArrayList<>();

    public BatchFactory() {
        batches.add(BatchRequestDTO.builder()
                .id(1L)
                .amount(1.5)
                .time(LocalTime.of(10, 00, 00))
                .duration(30)
                .vehicleId(1L)
                .build());
        batches.add(BatchRequestDTO.builder()
                .id(2L)
                .amount(3.5)
                .time(LocalTime.of(10, 40, 00))
                .duration(20)
                .vehicleId(2L)
                .build());
    }
}
