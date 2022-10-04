package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.vehicle.service.VehicleGlobalDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class OrderBatchServiceTest {
    @Mock
    private OrderBatchCustomMethods repository;
    @Mock
    private VehicleGlobalDao vehicleGlobalDao;
    private OrderBatchService service;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        service = new OrderBatchService(repository, vehicleGlobalDao);
    }

    @Test
    @DisplayName("throws ElementNotFoundException when orderBatch not exist")
    void readOrderBatch_throwsElementNotFoundException(){
        when(repository.existsById(anyLong())).thenReturn(false);
        var exception = catchThrowable(()-> service.readOrderBatch(anyLong()));
        assertThat(exception).isInstanceOf(ElementNotFoundException.class);
    }

}