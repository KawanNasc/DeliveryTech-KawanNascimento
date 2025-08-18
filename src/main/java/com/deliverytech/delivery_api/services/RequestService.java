package com.deliverytech.delivery_api.services;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Request createRequest(Long clientId, Long restaurantId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clientId));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant não encontrado: " + restaurantId));

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
    public Request addItem(Long requestId, Long productId, Integer quantity) {
        Request request = findPerId(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + requestId));

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
        item.setRequests(request);
        item.setProducts(product);
        item.setQuantity(quantity);
        item.setUnitaryPrice(product.getPrice());
        item.calculateSubtotal();

        request.addItem(item);

        return requestRepository.save(request);
    }

    // Confirmar pedido
    public Request confirmRequest(Long requestId) {
        Request request = findPerId(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + requestId));

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

    @Transactional(readOnly = true)
    public Optional<Request> findPerId(Long id) {
        return requestRepository.findById(id);
    }

    // Listar pedidos p/ cliente
    @Transactional(readOnly = true)
    public List<Request> listPerClient(Long clientId) {
        return requestRepository.findByClientIdOrderByDateRequestDesc(clientId);
    }

    // Buscar p/ n° do pedido
    @Transactional(readOnly = true)
    public Optional<Request> findPerNumber(String numberRequest) {
        return Optional.ofNullable(requestRepository.findByNumberRequest(numberRequest));
    }

    // Cancelar pedido
    public Request cancelRequest(Long requestId, String reason) {
        Request request = findPerId(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + requestId));

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
}
