package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.model.Request;

import com.deliverytech.delivery_api.services.RequestService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

// Antigos imports
// import com.deliverytech.delivery_api.data.request.RequestDTORequest;
// import com.deliverytech.delivery_api.data.request.CalculateRequestDTORequest;
// import com.deliverytech.delivery_api.data.response.RequestDTOResponse;
// import com.deliverytech.delivery_api.data.response.CalculateRequestDTOResponse;
// import com.deliverytech.delivery_api.config.ApiResponseWrapper;
// import com.deliverytech.delivery_api.config.PagedResponseWrapper;
// import com.deliverytech.delivery_api.data.request.StatusRequestDTORequest;

// import com.deliverytech.delivery_api.services.interfaces.RequestServiceInterface;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;

// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;

// import org.springframework.format.annotation.DateTimeFormat;
// import org.springframework.http.HttpStatus;

// import java.time.LocalDateTime;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins = "*")
@Tag(name = "Requests", description = "Operations related with request")
public class RequestController {
        @Autowired
        private RequestService requestService;

        @PostMapping
        @PreAuthorize("hasRole('CLIENT')")
        public ResponseEntity<Request> create(@RequestParam Long clientId,
                        @RequestParam Long restaurantId) {
                Request newRequest = requestService.createRequest(clientId, restaurantId);
                return ResponseEntity.status(201).body(newRequest);
        }

        @GetMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<Request>> list() {
                List<Request> requests = requestService.listAll();
                return ResponseEntity.ok(requests);
        }

        @GetMapping("/my")
        @PreAuthorize("hasRole('CLIENT')")
        public ResponseEntity<Optional<Request>> listMyRequests(@Valid @RequestBody Long id) {
                Optional<Request> requests = requestService.findPerId(id);
                return ResponseEntity.ok(requests);
        }

        // @Autowired
        // private RequestService requestService;

        // @Autowired
        // private RequestServiceInterface requestServiceInt;

        // // Criar novo pedido
        // @PostMapping
        // @Operation(summary = "Create request", description = "Creates a new request
        // in the system")
        // @ApiResponses({
        // @ApiResponse(responseCode = "201", description = "request created
        // successfully"),
        // @ApiResponse(responseCode = "400", description = "Invalid data"),
        // @ApiResponse(responseCode = "404", description = "Customer or restaurant not
        // found"),
        // @ApiResponse(responseCode = "409", description = "Product unavailable")
        // })
        // public ResponseEntity<ApiResponseWrapper<RequestDTOResponse>> createRequest(
        // @Valid @RequestBody @Parameter(description = "Data of the request to be
        // created") RequestDTORequest dto) {
        // RequestDTOResponse request = requestServiceInt.createRequest(dto);
        // ApiResponseWrapper<RequestDTOResponse> response = new
        // ApiResponseWrapper<>(true, request,
        // "Request created successfully");
        // return ResponseEntity.status(HttpStatus.CREATED).body(response);
        // }

        // // Calcular o total do pedido
        // @PostMapping("/calculate")
        // @Operation(summary = "Calculate order total", description = "Calculates the
        // total of an order without saving it")
        // @ApiResponses({
        // @ApiResponse(responseCode = "200", description = "Total calculated
        // successfully"),
        // @ApiResponse(responseCode = "400", description = "Invalid data"),
        // @ApiResponse(responseCode = "404", description = "Product not found")
        // })
        // public ResponseEntity<ApiResponseWrapper<CalculateRequestDTOResponse>>
        // calculateTotal(
        // @Valid @RequestBody
        // @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Items
        // for calculation") CalculateRequestDTORequest dto) {
        // CalculateRequestDTOResponse calculation =
        // requestServiceInt.calculateTotalRequest(dto);
        // ApiResponseWrapper<CalculateRequestDTOResponse> response = new
        // ApiResponseWrapper<>(true, calculation,
        // "Total calculated successfully");
        // return ResponseEntity.ok(response);
        // }

        // // Buscar todos os pedidos
        // @GetMapping
        // @Operation(summary = "List requests", description = "Lists requests with
        // optional filters and pagination")
        // @ApiResponses({
        // @ApiResponse(responseCode = "200", description = "List retrieved
        // successfully")
        // })
        // public ResponseEntity<PagedResponseWrapper<RequestDTOResponse>> list(
        // @Parameter(description = "Start date") @RequestParam(required = false)
        // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
        // @Parameter(description = "End date") @RequestParam(required = false)
        // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate,
        // @Parameter(description = "Pagination parameters") Pageable pageable) {
        // Page<RequestDTOResponse> requests = requestServiceInt.listRequest(startDate,
        // endDate, pageable);
        // PagedResponseWrapper<RequestDTOResponse> response = new
        // PagedResponseWrapper<>(requests);
        // return ResponseEntity.ok(response);

        // }

        // // Buscar pedido p/ ID
        // @GetMapping("/{id}")
        // @Operation(summary = "Find request by ID", description = "Retrieves a
        // specific request with all details")
        // @ApiResponses({
        // @ApiResponse(responseCode = "200", description = "Request found"),
        // @ApiResponse(responseCode = "404", description = "Request not found")
        // })
        // public ResponseEntity<ApiResponseWrapper<RequestDTOResponse>> findById(
        // @Parameter(description = "Request ID") @PathVariable Long id) {
        // RequestDTOResponse request = requestServiceInt.findRequestPerId(id);
        // ApiResponseWrapper<RequestDTOResponse> response = new
        // ApiResponseWrapper<>(true, request,
        // "Request found");
        // return ResponseEntity.ok(response);
        // }

        // // Listar pedidos p/ cliente
        // @GetMapping("/client/{clientId}")
        // public ResponseEntity<List<RequestDTOResponse>> listPerClient(@PathVariable
        // Long clientId) {
        // List<RequestDTOResponse> requests =
        // requestServiceInt.findRequestsPerClient(clientId);
        // return ResponseEntity.ok(requests);
        // }

        // // Atualizar status do pedido
        // @PutMapping("/{id}/status")
        // @Operation(summary = "Update request status", description = "Updates the
        // status of an request")
        // @ApiResponses({
        // @ApiResponse(responseCode = "200", description = "Status updated
        // successfully"),
        // @ApiResponse(responseCode = "404", description = "Request not found"),
        // @ApiResponse(responseCode = "400", description = "Invalid status transition")
        // })
        // public ResponseEntity<ApiResponseWrapper<RequestDTOResponse>>
        // updateStatusRequest(
        // @Parameter(description = "Request ID") @PathVariable Long id,
        // @Valid @RequestBody StatusRequestDTORequest statusRequestDTO) {
        // RequestDTOResponse request = requestServiceInt.updateStatusRequesT(id,
        // statusRequestDTO);
        // ApiResponseWrapper<RequestDTOResponse> response = new
        // ApiResponseWrapper<>(true, request,
        // "Status updated successfully");
        // return ResponseEntity.ok(response);
        // }

        // // Excluir pedido
        // @DeleteMapping("/{id}")
        // @Operation(summary = "Cancel order", description = "Cancels an request if
        // possible")
        // @ApiResponses({
        // @ApiResponse(responseCode = "204", description = "Request canceled
        // successfully"),
        // @ApiResponse(responseCode = "404", description = "Request not found"),
        // @ApiResponse(responseCode = "400", description = "Request cannot be
        // canceled")
        // })
        // public ResponseEntity<Void> cancelRequest(@Parameter(description = "Request
        // ID") @PathVariable Long id) {
        // requestServiceInt.cancelRequest(id);
        // return ResponseEntity.noContent().build();
        // }

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
        // @RequestParam(required = false) String reason) {
        // try {
        // Request request = requestService.cancelRequest(requestId, reason);
        // return ResponseEntity.ok(request);
        // } catch (IllegalArgumentException e) {
        // return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        // } catch (Exception e) {
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro
        // interno do servidor");
        // }
        // }
}