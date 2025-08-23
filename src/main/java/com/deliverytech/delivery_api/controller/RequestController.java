package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.model.Request;
import com.deliverytech.delivery_api.enums.StatusRequest;

import com.deliverytech.delivery_api.services.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins = "*")
public class RequestController {

    @Autowired
    private RequestService requestService;

    // Criar novo pedido
    @PostMapping
    public ResponseEntity<?> createRequest(@RequestParam Long clientId, @RequestParam Long restaurantId) {
        try {
            Request request = requestService.createRequest(clientId, restaurantId);
            return ResponseEntity.status(HttpStatus.CREATED).body(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    // Adicionar item ao pedido
    @PostMapping("/{requestId}/itens")
    public ResponseEntity<?> addItem(@PathVariable Long requestId, @RequestParam Long productId,
            @RequestParam Integer quantity) {
        try {
            Request request = requestService.addItem(requestId, productId, quantity);
            return ResponseEntity.ok(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

     // Buscar pedido p/ ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findPerId(@PathVariable Long id) {
        Optional<Request> request = requestService.findPerId(id);

        if (request.isPresent()) {
            return ResponseEntity.ok(request.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Listar pedidos p/ cliente
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Request>> listPerClient(@PathVariable Long clientId) {
        List<Request> requests = requestService.listPerClient(clientId);
        return ResponseEntity.ok(requests);
    }

    // Confirmar pedido
    @PutMapping("/{requestId}/confirm")
    public ResponseEntity<?> confirmRquest(@PathVariable Long requestId) {
        try {
            Request request = requestService.confirmRequest(requestId);
            return ResponseEntity.ok(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    // Atualizar status do pedido
    @PutMapping("/{requestId}/status")
    public ResponseEntity<?> updateStatusRequest(@PathVariable Long requestId, @RequestParam StatusRequest statusRequest) {
        try {
            // Falta o updateStatusRequest
            Request request = requestService.updateStatusRequest(requestId, statusRequest);

            return ResponseEntity.ok(request);
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    // Cancelar pedido
    @PutMapping("/{requestId}/cancel")
    // Falta o atributo reason
    public ResponseEntity<?> cancelRequest(@PathVariable Long requestId, @RequestParam(required = false) String reason) {
        try {
            Request request = requestService.cancelRequest(requestId, reason);
            return ResponseEntity.ok(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
}
