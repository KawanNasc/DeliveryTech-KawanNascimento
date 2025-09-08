package com.deliverytech.delivery_api.services.impl;

import com.deliverytech.delivery_api.data.request.ClientDTORequest;
import com.deliverytech.delivery_api.data.response.ClientDTOResponse;

import com.deliverytech.delivery_api.model.Client;

import com.deliverytech.delivery_api.repository.ClientRepository;

import com.deliverytech.delivery_api.services.interfaces.ClientServiceInterface;

import com.deliverytech.delivery_api.exception.BusinessException;
import com.deliverytech.delivery_api.exception.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientServiceInterface {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ClientDTOResponse registerClient(ClientDTORequest dto) {
        // Validar e-mail único
        if (clientRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + dto.getEmail());
        }

        // Converter DTO
        Client client = modelMapper.map(dto, Client.class);
        client.setActive(true);

        // Salvar cliente
        Client savedClient = clientRepository.save(client);

        // Retornar DTO de resposta
        return modelMapper.map(savedClient, ClientDTOResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDTOResponse findClientPerId(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado c/ id: " + id));

        return modelMapper.map(client, ClientDTOResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDTOResponse findClientPerEmail(String email) {
        Client client = clientRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado c/ e-mail: " + email));

        return modelMapper.map(client, ClientDTOResponse.class);
    }

    @Override
    public ClientDTOResponse updateClient(Long id, ClientDTORequest dto) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado c/ id: " + id));

        // Validar e-mail único (Se mudou)
        if (!client.getEmail().equals(dto.getEmail()) && clientRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("E-mail já cadastrado: "  + dto.getEmail());
        }

        // Atualizar dados
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        client.setAddress(dto.getAddress());

        Client updatedClient = clientRepository.save(client);
        return modelMapper.map(updatedClient, ClientDTOResponse.class);
    }

    @Override
    public ClientDTOResponse activateDesativateClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado c/ id: " + id));

        client.setActive(!client.isActive());
        Client updatedClient = clientRepository.save(client);

        return modelMapper.map(updatedClient, ClientDTOResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTOResponse> listActiveClients() {
        List<Client> activeClients =  clientRepository.findByActiveTrue();

        return activeClients.stream()
                .map(client -> modelMapper.map(client, ClientDTOResponse.class))
                .collect(Collectors.toList());
    }
}