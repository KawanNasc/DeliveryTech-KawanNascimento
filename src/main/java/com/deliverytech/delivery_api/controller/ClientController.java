package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.model.Client;

import com.deliverytech.delivery_api.services.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Cadastrar novo cliente
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody Client client) {
        try {
            Client saveClient = clientService.register(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    // Listar todos os clientes ativos
    @GetMapping
    public ResponseEntity<List<Client>> list() {
        List<Client> clients = clientService.listActive();
        return ResponseEntity.ok(clients);
    }
    
    // Buscar cliente p/ ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findPerId(@PathVariable Long id) {
        Optional<Client> client = clientService.findPerId(id);

        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar clientes p/ nome
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Client>> findPerName(@PathVariable String name) {
        List<Client> clients = clientService.findPerName(name);
        return ResponseEntity.ok(clients);
    }

    // Buscar clientes p/ e-mail
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findPerEmail(@PathVariable String email) {
        Optional<Client> client = clientService.findPerEmail(email);
        
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atuaiizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Client client) {
        try {
            Client updatedClient = clientService.update(id, client);
            return ResponseEntity.ok(updatedClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    // Inativar cliente (Soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> inative(@PathVariable Long id) {
        try {
            clientService.inative(id);
            return ResponseEntity.ok().body("Cliente inativado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
}
