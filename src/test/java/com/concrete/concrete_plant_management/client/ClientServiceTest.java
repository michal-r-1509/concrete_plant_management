package com.concrete.concrete_plant_management.client;

import com.concrete.concrete_plant_management.exceptions.ElementConflictException;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import com.concrete.concrete_plant_management.order.OrderGlobalDao;
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

class ClientServiceTest {

    @Mock
    private ClientCustomMethods repository;
    @Mock
    private OrderGlobalDao orderGlobalDao;
    private ClientService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        service = new ClientService(repository, orderGlobalDao);
    }

    @Test
    @DisplayName("throws ElementConflictException when client by nip number exists")
    void saveClient_throwsElementConflictException() {
        when(repository.existsClientByNip(anyLong())).thenReturn(true);
        var exception = catchThrowable(() -> service.saveClient(getClient()));
        assertThat(exception).isInstanceOf(ElementConflictException.class);
    }

    @Test
    @DisplayName("throws ElementNotFoundException when client not exists")
    void updateClient_throwsElementNotFoundException() {
        when(repository.existsById(anyInt())).thenReturn(false);
        var exception = catchThrowable(() -> service.updateClient(anyInt(), getClient()));
        assertThat(exception).isInstanceOf(ElementNotFoundException.class);
    }

    @Test
    @DisplayName("throws ElementConflictException when client exists in active order")
    void deleteClient_throwsElementConflictException() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(orderGlobalDao.existsOrderByClientId(anyInt())).thenReturn(true);
        var exception = catchThrowable(() -> service.deleteClient(anyInt()));
        assertThat(exception).isInstanceOf(ElementConflictException.class);
    }

    private Client getClient() {
        Client client = new Client();
        client.setName("");
        return client;
    }
}