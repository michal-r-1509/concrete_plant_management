package com.concrete.concrete_plant_management.vehicle;

import com.concrete.concrete_plant_management.exceptions.ElementConflictException;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order_batch.OrderBatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class VehicleServiceTest {

    @Mock
    private VehicleCustomMethods repository;
    @Mock
    private OrderBatchService orderBatchService;
    private VehicleService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        service = new VehicleService(repository, orderBatchService);
    }

    @Test
    @DisplayName("throws ElementConflictException when vehicle with registry number already exists")
    void saveVehicle_throwsElementConflictException(){
        Vehicle vehicle = getVehicleObject();
        when(repository.existsByRegNo(anyString())).thenReturn(true);

        var exception = catchThrowable(() -> service.saveVehicle(vehicle));
        assertThat(exception).isInstanceOf(ElementConflictException.class);
    }

    @Test
    @DisplayName("throws ElementNotFoundException when vehicle not exists")
    void updateVehicle_throwsElementNotFoundException(){
        Vehicle vehicle = getVehicleObject();
        when(repository.existsById(anyInt())).thenReturn(false);

        var exception = catchThrowable(() -> service.updateVehicle(anyInt(), vehicle));
        assertThat(exception).isInstanceOf(ElementNotFoundException.class);
    }

    @Test
    @DisplayName("throws ElementConflictException when vehicle exists and is used in any orderBatch")
    void deleteVehicle_throwsElementConflictException() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(orderBatchService.existsOrderBatchByVehicleId(anyInt())).thenReturn(true);

        var exception = catchThrowable(() -> service.deleteVehicle(anyInt()));
        assertThat(exception).isInstanceOf(ElementConflictException.class);
    }

    private Vehicle getVehicleObject(){
        Vehicle vehicle = new Vehicle();
        vehicle.setName("");
        vehicle.setRegNo("");
        return vehicle;
    }
}