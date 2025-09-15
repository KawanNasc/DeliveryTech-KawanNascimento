package com.deliverytech.delivery_api.services;

import com.deliverytech.delivery_api.data.request.ClientDTORequest;
import com.deliverytech.delivery_api.data.response.ClientDTOResponse;
import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.repository.ClientRepository;
import com.deliverytech.delivery_api.services.impl.ClientServiceImpl;
import com.deliverytech.delivery_api.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ClientService")
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    private Client client;
    private ClientDTORequest clientDTORequest;
    private ClientDTOResponse clientDTOResponse;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setName("João Silva");
        client.setEmail("joao@email.com");
        client.setPhone("11999999999");
        client.setAddress(null);
        client.setActive(true);
        client.setCpf("12345678900");

        clientDTORequest = new ClientDTORequest();
        clientDTORequest.setName("João Silva");
        clientDTORequest.setEmail("joao@email.com");
        clientDTORequest.setPhone("11999999999");
        clientDTORequest.setAddress(null);
        clientDTORequest.setCpf("12345678900");

        clientDTOResponse = new ClientDTOResponse();
        clientDTOResponse.setId(1L);
        clientDTOResponse.setName("João Silva");
        clientDTOResponse.setEmail("joao@email.com");
        clientDTOResponse.setPhone("11999999999");
        clientDTOResponse.setAddress(null);
        clientDTOResponse.setActive(true);
    }

    @Test
    @DisplayName("Deve salvar cliente com dados válidos")
    void should_SaveClient_When_ValidData() {
        // Given
        when(clientRepository.existsByEmail("joao@email.com")).thenReturn(false);
        when(clientRepository.existsByCpf("12345678900")).thenReturn(false);
        when(modelMapper.map(clientDTORequest, Client.class)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(modelMapper.map(client, ClientDTOResponse.class)).thenReturn(clientDTOResponse);

        // When
        ClientDTOResponse result = clientServiceImpl.saveClient(clientDTORequest);

        // Then
        assertNotNull(result);
        assertEquals("João Silva", result.getName());
        assertEquals("joao@email.com", result.getEmail());
        verify(clientRepository).existsByEmail("joao@email.com");
        verify(clientRepository).existsByCpf("12345678900");
        verify(clientRepository).save(client);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF já existe")
    void should_ThrowException_When_CpfAlreadyExists() {
        // Given
        when(clientRepository.existsByCpf("12345678900")).thenReturn(true);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> clientServiceImpl.saveClient(clientDTORequest));
        verify(clientRepository).existsByCpf("12345678900");
    }

    @Test
    @DisplayName("Deve buscar cliente por ID existente")
    void should_ReturnClient_When_IdExists() {
        // Given
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(modelMapper.map(client, ClientDTOResponse.class)).thenReturn(clientDTOResponse);

        // When
        ClientDTOResponse resultado = clientServiceImpl.findClientPerId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getName());
        verify(clientRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID não existe")
    void should_ThrowException_When_IdNotExists() {
        // Given
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> clientServiceImpl.findClientPerId(999L));

        assertEquals("Cliente não encontrado c/ id: 999", exception.getMessage());
        verify(clientRepository).findById(999L);
    }

    @Test
    @DisplayName("Deve listar clientes com paginação")
    void should_ReturnPagedClients_When_RequestedWithPagination() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Client> page = new PageImpl<>(Arrays.asList(client));
        when(clientRepository.findByActiveTrue(pageable)).thenReturn(page);
        when(modelMapper.map(client, ClientDTOResponse.class)).thenReturn(clientDTOResponse);

        // When
        Page<ClientDTOResponse> resultado = clientServiceImpl.listActiveClients(pageable);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("João Silva", resultado.getContent().get(0).getName());
        verify(clientRepository).findByActiveTrue(pageable);
    }

    @Test
    @DisplayName("Deve atualizar cliente existente")
    void should_UpdateClient_When_ClientExists() {
        // Given
        ClientDTORequest clienteAtualizado = new ClientDTORequest();
        clienteAtualizado.setName("João Santos");
        clienteAtualizado.setEmail("joao.santos@email.com");
        clienteAtualizado.setPhone("11999999999");
        clienteAtualizado.setAddress(null);
        clienteAtualizado.setCpf("12345678900");

        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setName("João Santos");
        updatedClient.setEmail("joao.santos@email.com");
        updatedClient.setPhone("11999999999");
        updatedClient.setAddress(null);
        updatedClient.setActive(true);
        updatedClient.setCpf("12345678900");

        ClientDTOResponse updatedResponse = new ClientDTOResponse();
        updatedResponse.setId(1L);
        updatedResponse.setName("João Santos");
        updatedResponse.setEmail("joao.santos@email.com");
        updatedResponse.setPhone("11999999999");
        updatedResponse.setAddress(null);
        updatedResponse.setActive(true);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.existsByEmail("joao.santos@email.com")).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);
        when(modelMapper.map(updatedClient, ClientDTOResponse.class)).thenReturn(updatedResponse);

        // When
        ClientDTOResponse resultado = clientServiceImpl.updateClient(1L, clienteAtualizado);

        // Then
        assertNotNull(resultado);
        assertEquals("João Santos", resultado.getName());
        assertEquals("joao.santos@email.com", resultado.getEmail());
        verify(clientRepository).findById(1L);
        verify(clientRepository).existsByEmail("joao.santos@email.com");
        verify(clientRepository).save(any(Client.class));
    }
}