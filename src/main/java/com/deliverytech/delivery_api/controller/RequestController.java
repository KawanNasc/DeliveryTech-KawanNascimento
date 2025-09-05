package com.deliverytech.delivery_api.controller;

// import com.deliverytech.delivery_api.model.Request;
import com.deliverytech.delivery_api.enums.StatusRequest;

import com.deliverytech.delivery_api.data.request.RequestDTORequest;
import com.deliverytech.delivery_api.data.response.RequestDTOResponse;
import com.deliverytech.delivery_api.data.ItemRequestDTO;

// import com.deliverytech.delivery_api.services.RequestService;

import com.deliverytech.delivery_api.services.interfaces.RequestServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins = "*")
public class RequestController {

    // @Autowired
    // private RequestService requestService;

    @Autowired
    private RequestServiceInterface requestServiceInt;

    // Criar novo pedido
    @PostMapping
    public ResponseEntity<RequestDTOResponse> createRequest(@RequestBody RequestDTORequest dto) {
        RequestDTOResponse request = requestServiceInt.createRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    // Calcular o total do pedido
    @PostMapping("/calcular")
    public ResponseEntity<BigDecimal> calculateTotalRequest(@Valid @RequestBody List<ItemRequestDTO> items) {
        BigDecimal total = requestServiceInt.calculateTotalRequest(items);
        return ResponseEntity.ok(total);
    }

    // Buscar pedido p/ ID
    @GetMapping("/{id}")
    public ResponseEntity<RequestDTOResponse> findPerId(@PathVariable Long id) {
        RequestDTOResponse request = requestServiceInt.findRequestPerId(id);
        return ResponseEntity.ok(request);
    }

    // Listar pedidos p/ cliente
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<RequestDTOResponse>> listPerClient(@PathVariable Long clientId) {
        List<RequestDTOResponse> requests = requestServiceInt.findRequestsPerClient(clientId);
        return ResponseEntity.ok(requests);
    }

    // Atualizar status do pedido
    @PutMapping("/{id}/status")
    public ResponseEntity<RequestDTOResponse> updateStatusRequest(@PathVariable Long id,
            @RequestBody StatusRequest statusRequest) {
        // Falta o updateStatusRequest
        RequestDTOResponse request = requestServiceInt.updateStatusRequesT(id, statusRequest);

        return ResponseEntity.ok(request);
    }

    // Excluir pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelRequest(@PathVariable Long id) {
        requestServiceInt.cancelRequest(id);
        return ResponseEntity.noContent().build();
    }

    // EM CASO DE ESTAR UTILIZANDO MODEL

    // // Criar novo pedido
    // @PostMapping
    // public ResponseEntity<?> createRequest(@RequestParam Long clientId,
    // @RequestParam Long restaurantId) {
    // try {
    // Request request = requestService.createRequest(clientId, restaurantId);
    // return ResponseEntity.status(HttpStatus.CREATED).body(request);
    // } catch (IllegalArgumentException e) {
    // return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro
    // interno do servidor");
    // }
    // }

    // // Adicionar item ao pedido
    // @PostMapping("/{id}/itens")
    // public ResponseEntity<?> addItem(@PathVariable Long id, @RequestParam Long
    // productId,
    // @RequestParam Integer quantity) {
    // try {
    // Request request = requestService.addItem(id, productId, quantity);
    // return ResponseEntity.ok(request);
    // } catch (IllegalArgumentException e) {
    // return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro
    // interno do servidor");
    // }
    // }

    // // Buscar pedido p/ ID
    // @GetMapping("/{id}")
    // public ResponseEntity<?> findPerId(@PathVariable Long id) {
    // Optional<Request> request = requestService.findPerId(id);

    // if (request.isPresent()) {
    // return ResponseEntity.ok(request.get());
    // } else {
    // return ResponseEntity.notFound().build();
    // }
    // }

    // // Listar pedidos p/ cliente
    // @GetMapping("/client/{clientId}")
    // public ResponseEntity<List<Request>> listPerClient(@PathVariable Long
    // clientId) {
    // List<Request> requests = requestService.listPerClient(clientId);
    // return ResponseEntity.ok(requests);
    // }

    // // Confirmar pedido
    // @PutMapping("/{id}/confirm")
    // public ResponseEntity<?> confirmRquest(@PathVariable Long id) {
    // try {
    // Request request = requestService.confirmRequest(id);
    // return ResponseEntity.ok(request);
    // } catch (IllegalArgumentException e) {
    // return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro
    // interno do servidor");
    // }
    // }

    // // Atualizar status do pedido
    // @PutMapping("/{id}/status")
    // public ResponseEntity<?> updateStatusRequest(@PathVariable Long id,
    // @RequestParam StatusRequest statusRequest) {
    // try {
    // // Falta o updateStatusRequest
    // Request request = requestService.updateStatusRequest(id, statusRequest);

    // return ResponseEntity.ok(request);
    // } catch (IllegalArgumentException e) {
    // return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro
    // interno do servidor");
    // }
    // }

    // // Cancelar pedido
    // @PutMapping("/{requestId}/cancel")
    // // Falta o atributo reason
    // public ResponseEntity<?> cancelRequest(@PathVariable Long requestId,
    //         @RequestParam(required = false) String reason) {
    //     try {
    //         Request request = requestService.cancelRequest(requestId, reason);
    //         return ResponseEntity.ok(request);
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
    //     }
    // }
}
