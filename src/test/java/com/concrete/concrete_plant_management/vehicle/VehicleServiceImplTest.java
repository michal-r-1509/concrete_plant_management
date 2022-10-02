package com.concrete.concrete_plant_management.vehicle;

import com.concrete.concrete_plant_management.exceptions.ElementConflictException;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order_batch.OrderBatchService;
import com.concrete.concrete_plant_management.vehicle.dto.VehicleRequestDTO;
import com.concrete.concrete_plant_management.vehicle.repository.VehicleRepositoryMethods;
import com.concrete.concrete_plant_management.vehicle.service.VehicleServiceImpl;
import com.concrete.concrete_plant_management.vehicle.tool.VehicleType;
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

class VehicleServiceImplTest {

    @Mock
    private VehicleRepositoryMethods repository;
    @Mock
    private OrderBatchService orderBatchService;
    private VehicleServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        service = new VehicleServiceImpl(repository, orderBatchService);
    }

    @Test
    @DisplayName("throws ElementConflictException when vehicle with registry number already exists")
    void saveVehicle_throwsElementConflictException(){
        VehicleRequestDTO vehicle = getVehicleDTO();
        when(repository.existsByRegNo(anyString())).thenReturn(true);

        var exception = catchThrowable(() -> service.saveVehicle(vehicle));
        assertThat(exception).isInstanceOf(ElementConflictException.class);
    }

    @Test
    @DisplayName("throws ElementNotFoundException when vehicle not exists")
    void updateVehicle_throwsElementNotFoundException(){
        VehicleRequestDTO vehicle = getVehicleDTO();
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

    private VehicleRequestDTO getVehicleDTO(){
        VehicleRequestDTO vehicle = VehicleRequestDTO.builder()
                .name("")
                .type(VehicleType.MIXER)
                .regNo("")
                .capacity(9.0)
                .pumpLength(24)
                .build();
        return vehicle;
    }
}