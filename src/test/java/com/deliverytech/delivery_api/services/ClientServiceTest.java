package com.deliverytech.delivery_api.services;

import com.deliverytech.delivery_api.data.request.ClientDTORequest;
import com.deliverytech.delivery_api.data.response.ClientDTOResponse;

import com.deliverytech.delivery_api.model.Client;

import com.deliverytech.delivery_api.repository.ClientRepository;

import com.deliverytech.delivery_api.services.impl.ClientServiceImpl;

import com.deliverytech.delivery_api.exception.BusinessException;

import org.modelmapper.ModelMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    @Test
    void shouldRegisterClientWithSucess() {
        //  Givem
        ClientDTORequest clientDTORequest = new ClientDTORequest();
        clientDTORequest.setName("João  Silva");
        clientDTORequest.setEmail("joao@email.com");
        clientDTORequest.setPhone("11999999999");
        clientDTORequest.setAddress("Rua A, 123");

        Client client = new Client();
        client.setId(1L);
        client.setName("João Silva");
        client.setActive(true);

        ClientDTOResponse clientDTOResponse = new ClientDTOResponse();
        clientDTOResponse.setId(1L);
        clientDTOResponse.setName("João Silva");
        clientDTOResponse.setEmail("joao@email.com");
        clientDTOResponse.setActive(true);

        when(clientRepository.existsByEmail(clientDTORequest.getEmail())).thenReturn(false);
        when(modelMapper.map(clientDTORequest, Client.class)).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(modelMapper.map(client, ClientDTOResponse.class)).thenReturn(clientDTOResponse);

        // When
        ClientDTOResponse result = clientServiceImpl.registerClient(clientDTORequest);

        // Then
        assertNotNull(result);
        assertEquals("João Silva", result.getName());
        assertEquals("joao@email.com", result.getEmail());
        assertTrue(result.isActive());

        verify(clientRepository).existsByEmail(clientDTORequest.getEmail());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void shouldRejectClientWithDuplicatedEmail() {
        // Given
        ClientDTORequest clientDTORequest = new ClientDTORequest();
        clientDTORequest.setEmail("joao@gmail.com");

        when(clientRepository.existsByEmail(clientDTORequest.getEmail())).thenReturn(true);

        // When & Then
        BusinessException exception = assertThrows(
            BusinessException.class,
            () -> clientServiceImpl.registerClient(clientDTORequest)
        );

        assertEquals("E-mail já cadastrado: joao@email.com", exception.getMessage());

        verify(clientRepository, never()).save(any(Client.class));
    }
}
