package com.concrete.concrete_plant_management.order;

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
import static org.mockito.Mockito.when;

class OrderServiceTest {
    @Mock
    private OrderCustomMethods repository;
    @Mock
    private OrderBatchService orderBatchService;
    private OrderService service;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        service = new OrderService(repository, orderBatchService);
    }

    @Test
    @DisplayName("throws ElementNotFoundException when order not exist")
    void updateOrder_throwsElementNotFoundException(){
        when(repository.existsById(anyInt())).thenReturn(false);
        var exception = catchThrowable(()-> service.updateOrder(anyInt(), null));
        assertThat(exception).isInstanceOf(ElementNotFoundException.class);
    }
}