package com.concrete.concrete_plant_management.order;

import com.concrete.concrete_plant_management.client.service.ClientService;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order.repository.OrderRepositoryMethods;
import com.concrete.concrete_plant_management.order.service.OrderServiceImpl;
import com.concrete.concrete_plant_management.order.tool.OrderMapper;
import com.concrete.concrete_plant_management.order_batch.OrderBatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {
    @Mock
    private OrderRepositoryMethods repository;
    @Mock
    private OrderBatchService orderBatchService;
    private OrderServiceImpl service;
    private OrderMapper mapper;
    private ClientService clientService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        service = new OrderServiceImpl(repository, mapper, orderBatchService, clientService);
    }

    @Test
    @DisplayName("throws ElementNotFoundException when order not exist")
    void updateOrder_throwsElementNotFoundException(){
        when(repository.existsById(anyLong())).thenReturn(false);
        var exception = catchThrowable(()-> service.updateOrder(anyLong(), null));
        assertThat(exception).isInstanceOf(ElementNotFoundException.class);
    }
}