package com.concrete.concrete_plant_management.order.batch.tool;

import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.domain.Schedule;
import com.concrete.concrete_plant_management.domain.Vehicle;
import com.concrete.concrete_plant_management.order.batch.dto.BatchRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class ScheduleMapper {

    public Schedule toSchedule(final Order order, final BatchRequestDTO batchReqDTO, final Vehicle vehicle) {
        return Schedule.builder()
                .date(order.getDate())
                .startTime(batchReqDTO.getTime())
                .endTime(batchReqDTO.getTime().plusMinutes(batchReqDTO.getDuration()))
                .vehicle(vehicle)
                .build();
    }

}
