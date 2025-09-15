package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.data.request.ClientDTORequest;
import com.deliverytech.delivery_api.data.response.ClientDTOResponse;
import com.deliverytech.delivery_api.services.impl.ClientServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientServiceImpl clientService;

    @Autowired
    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTOResponse createClient(@Valid @RequestBody ClientDTORequest dto) {
        return clientService.registerClient(dto);
    }

    @GetMapping("/{id}")
    public ClientDTOResponse findClientById(@PathVariable Long id) {
        return clientService.findClientPerId(id);
    }

    @GetMapping
    public Page<ClientDTOResponse> listActiveClients(Pageable pageable) {
        return clientService.listActiveClients(pageable);
    }

    @PutMapping("/{id}")
    public ClientDTOResponse updateClient(@PathVariable Long id, @Valid @RequestBody ClientDTORequest dto) {
        return clientService.updateClient(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        clientService.activateDesativateClient(id);
    }
}