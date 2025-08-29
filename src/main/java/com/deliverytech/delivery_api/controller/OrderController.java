package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.model.Order;
import com.deliverytech.delivery_api.enums.StatusOrder;

import com.deliverytech.delivery_api.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Criar novo pedido
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestParam Long clientId, @RequestParam Long restaurantId) {
        try {
            Order order = orderService.createOrder(clientId, restaurantId);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    // Adicionar item ao pedido
    @PostMapping("/{id}/itens")
    public ResponseEntity<?> addItem(@PathVariable Long id, @RequestParam Long productId,
            @RequestParam Integer quantity) {
        try {
            Order order = orderService.addItem(id, productId, quantity);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

     // Buscar pedido p/ ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findPerId(@PathVariable Long id) {
        Optional<Order> order = orderService.findPerId(id);

        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Listar pedidos p/ cliente
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Order>> listPerClient(@PathVariable Long clientId) {
        List<Order> order = orderService.listPerClient(clientId);
        return ResponseEntity.ok(order);
    }

    // Confirmar pedido
    @PutMapping("/{id}/confirm")
    public ResponseEntity<?> confirmRquest(@PathVariable Long id) {
        try {
            Order order = orderService.confirmOrder(id);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    // Atualizar status do pedido
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatuOrder(@PathVariable Long id, @RequestParam StatusOrder statusOrder) {
        try {
            // Falta o updateStatusOrder
            Order order = orderService.updateStatusOrder(id, statusOrder);

            return ResponseEntity.ok(order);
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    // Cancelar pedido
    @PutMapping("/{id}/cancel")
    // Falta o atributo reason
    public ResponseEntity<?> cancelOrder(@PathVariable Long id, @RequestParam(required = false) String reason) {
        try {
            Order order = orderService.cancelOrder(id, reason);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
}
