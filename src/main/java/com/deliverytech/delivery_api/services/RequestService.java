package com.deliverytech.delivery_api.services;

import com.deliverytech.delivery_api.data.request.SalesByRestaurantDTORequest;
import com.deliverytech.delivery_api.enums.StatusRequest;

import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.model.ItemRequest;
import com.deliverytech.delivery_api.model.Request;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.model.Product;

import com.deliverytech.delivery_api.repository.RequestRepository;
import com.deliverytech.delivery_api.repository.ClientRepository;
import com.deliverytech.delivery_api.repository.RestaurantRepository;
import com.deliverytech.delivery_api.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ProductRepository productRepository;

    // Criar novo pedido
    public Request createRequest(Long clientId, Long restaurant_id) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clientId));

        Restaurant restaurant = restaurantRepository.findById(restaurant_id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant não encontrado: " + restaurant_id));

        if (!client.isActive()) {
            throw new IllegalArgumentException("Cliente inativo, não pode fazer pedidos");
        }

        if (!restaurant.isActive()) {
            throw new IllegalArgumentException("Restaurante inativo, não pode fazer pedidos");
        }

        Request request = new Request();
        request.setClient(client);
        request.setRestaurant(restaurant);
        request.setStatusRequest(StatusRequest.PENDENTE);

        return requestRepository.save(request);
    }

    // Adicionar item ao pedido
    public Request addItem(Long id, Long productId, Integer quantity) {
        Request request = findPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + productId));

        if (!product.isAvailable()) {
            throw new IllegalArgumentException("Produto indisponível: " + product.getName());
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        if (!product.getRestaurant().getId().equals(request.getRestaurant().getId())) {
            throw new IllegalArgumentException("Produto não pertence ao restaurante do pedido");
        }

        ItemRequest item = new ItemRequest();
        item.setRequest(request);
        item.setProducts(product);
        item.setQuantity(quantity);
        item.setUnitaryPrice(product.getPrice());
        item.calculateSubtotal();

        request.addItem(item);

        return requestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public List<Request> listAll() {
        return requestRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Request> findPerId(Long id) {
        return requestRepository.findById(id);
    }

    // Confirmar pedido
    public Request confirmRequest(Long id) {
        Request request = findPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        if (request.getStatusRequest() != StatusRequest.PENDENTE) {
            throw new IllegalArgumentException("Apenas pedidos pendentes podem ser confirmados");
        }

        if (request.getItemsRequest().isEmpty()) {
            throw new IllegalArgumentException("Pedido deve ter pelo menos 1 item");
        }

        request.confirm();

        request.setStatusRequest(StatusRequest.CONFIRMADO);
        return requestRepository.save(request);
    }

    // Listar pedidos p/ cliente
    @Transactional(readOnly = true)
    public List<Request> listPerClient(Long clientId) {
        return requestRepository.findByClientIdOrderByDateRequestDesc(clientId);
    }

    // Cancelar pedido
    public Request cancelRequest(Long id, String reason) {
        Request request = findPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        if (request.getStatusRequest() == StatusRequest.ENTREGUE) {
            throw new IllegalArgumentException("Pedido já entregue, não pode ser cancelado");
        }

        if (request.getStatusRequest() == StatusRequest.CANCELADO) {
            throw new IllegalArgumentException("Pedido já está cancelado");
        }

        request.setStatusRequest(StatusRequest.CANCELADO);

        if (reason != null && !reason.trim().isEmpty()) {
            request.setNote(request.getNote() + " | Cancelado: " + reason);
        }

        return requestRepository.save(request);
    }

    public Request updateStatusRequest(Long id, StatusRequest statusRequest) {
        Request request = findPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        // Add business logic to validate status transition (e.g., only certain
        // transitions are allowed)
        if (request.getStatusRequest() == statusRequest) {
            throw new IllegalArgumentException("Request is already in this status");
        }

        request.setStatusRequest(statusRequest);
        return requestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public long countAllOrders() {
        return requestRepository.count();
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalRevenue() {
        return listAll().stream()
                .filter(r -> r.getStatusRequest() == StatusRequest.CONFIRMADO
                        || r.getStatusRequest() == StatusRequest.ENTREGUE)
                .map(Request::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalSalesBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Page<Request> requests = requestRepository.findByDateRequestBetweenOrderByDateRequestDesc(startDate, endDate,
                pageable);
        return requests.stream()
                .filter(r -> r.getStatusRequest() == StatusRequest.CONFIRMADO
                        || r.getStatusRequest() == StatusRequest.ENTREGUE)
                .map(Request::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional(readOnly = true)
    public long countRequestsBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return requestRepository.countByDateRequestBetween(startDate, endDate); // Corrected to return long
    }

    @Transactional(readOnly = true)
    public List<SalesByRestaurantDTORequest> getSalesByRestaurantBetween(LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable) {
        Page<Request> requests = requestRepository.findByDateRequestBetweenOrderByDateRequestDesc(startDate, endDate,
                pageable);
        Map<Restaurant, BigDecimal> salesMap = requests.stream()
                .filter(r -> r.getStatusRequest() == StatusRequest.CONFIRMADO
                        || r.getStatusRequest() == StatusRequest.ENTREGUE)
                .collect(Collectors.groupingBy(Request::getRestaurant,
                        Collectors.reducing(BigDecimal.ZERO, Request::getTotalValue, BigDecimal::add)));

        return salesMap.entrySet().stream()
                .map(entry -> {
                    SalesByRestaurantDTORequest dto = new SalesByRestaurantDTORequest();
                    dto.setRestaurantName(entry.getKey().getName());
                    dto.setTotal(entry.getValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}