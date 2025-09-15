package com.deliverytech.delivery_api.services;

import com.deliverytech.delivery_api.model.Client;

import com.deliverytech.delivery_api.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Cadastrar novo cliente
    public Client register(Client client) {

        // Validar e-mail único
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + client.getEmail());
        }

        if (clientRepository.existsByCpf(client.getCpf())) {
            // If it exists, throw a custom exception
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        // Validações de negócio
        validateDataClient(client);

        // Definir como ativo p/ padrão
        client.setActive(true);

        return clientRepository.save(client);
    }

    // Buscar cliente p/ ID
    @Transactional(readOnly = true)
    public Optional<Client> findPerId(Long id) {
        return clientRepository.findById(id);
    }

    // Buscar cliente p/ e-mail
    @Transactional(readOnly = true)
    public Optional<Client> findPerEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    // Listar todos os clientes ativos
    @Transactional(readOnly = true)
    public Page<Client> listActive(Pageable pageable) {
        return clientRepository.findByActiveTrue(pageable);
    }

    // Atualizar dados do cliente
    public Client update(Long id, Client updatedClient) {
        Client client = findPerId(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));

        // Verificar se e-mail não está sendo usado p/ outro cliente
        if (!client.getEmail().equals(updatedClient.getEmail())
                && clientRepository.existsByEmail(updatedClient.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + updatedClient.getEmail());
        }

        // Atualizar campos
        client.setName(updatedClient.getName());
        client.setEmail(updatedClient.getEmail());
        client.setPhone(updatedClient.getPhone());
        client.setAddress(updatedClient.getAddress());

        return clientRepository.save(client);
    }

    // Inativar cliente (Soft delete)
    public void inative(Long id) {
        Client client = findPerId(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));

        client.setActive(false);
        clientRepository.save(client);
    }

    // Buscar clientes p/ nome
    @Transactional(readOnly = true)
    public List<Client> findPerName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    private void validateDataClient(Client client) {
        if (client.getName() == null || client.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }

        if (client.getName().length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
        }
    }
}
