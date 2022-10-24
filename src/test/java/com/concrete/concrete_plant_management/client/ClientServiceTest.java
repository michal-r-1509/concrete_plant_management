package com.concrete.concrete_plant_management.client;

import com.concrete.concrete_plant_management.client.repository.ClientRepositoryMethods;
import com.concrete.concrete_plant_management.client.service.ClientService;
import com.concrete.concrete_plant_management.client.service.ClientServiceImpl;
import com.concrete.concrete_plant_management.client.tool.ClientMapper;
import com.concrete.concrete_plant_management.domain.Client;
import com.concrete.concrete_plant_management.exceptions.ElementConflictException;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order.repository.OrderRepositoryMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ClientServiceTest {

    @Mock
    private ClientRepositoryMethods repository;
    @Mock
    private ClientMapper mapper;
    private OrderRepositoryMethods orderRepository;
    private ClientService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        service = new ClientServiceImpl(repository, mapper, orderRepository);
    }

    @Test
    @DisplayName("throws ElementConflictException when client by nip number exists")
    void saveClient_throwsElementConflictException() {
        when(repository.existsClientByTaxpayerIdentNo(anyLong())).thenReturn(true);
        var exception = catchThrowable(() -> service.saveClient(getClient()));
        assertThat(exception).isInstanceOf(ElementConflictException.class);
    }

    @Test
    @DisplayName("throws ElementNotFoundException when client not exists")
    void updateClient_throwsElementNotFoundException() {
        when(repository.existsById(anyLong())).thenReturn(false);
        var exception = catchThrowable(() -> service.updateClient(anyLong(), getClient()));
        assertThat(exception).isInstanceOf(ElementNotFoundException.class);
    }

    @Test
    @DisplayName("throws ElementConflictException when client exists in active order")
    void deleteClient_throwsElementConflictException() {
        when(repository.existsById(anyLong())).thenReturn(true);
        when(orderRepository.existsOrderByClient_Id(anyLong())).thenReturn(true);
        var exception = catchThrowable(() -> service.deleteClient(anyLong()));
        assertThat(exception).isInstanceOf(ElementConflictException.class);
    }

    private Client getClient() {
        Client client = new Client();
        client.setName("");
        return client;
    }
}