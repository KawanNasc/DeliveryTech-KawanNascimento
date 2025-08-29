package com.deliverytech.delivery_api.services;

import com.deliverytech.delivery_api.enums.StatusOrder;

import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.model.ItemOrder;
import com.deliverytech.delivery_api.model.Order;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.model.Product;

import com.deliverytech.delivery_api.repository.OrderRepository;
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
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ProductRepository productRepository;

    // Criar novo pedido
    public Order createOrder(Long clientId, Long restaurant_id) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clientId));

        Restaurant restaurant = restaurantRepository.findById(restaurant_id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + restaurant_id));

        if (!client.isActive()) {
            throw new IllegalArgumentException("Cliente inativo, não pode fazer pedidos");
        }

        if (!restaurant.isActive()) {
            throw new IllegalArgumentException("Restaurante inativo, não pode fazer pedidos");
        }

        Order order = new Order();
        order.setClient(client);
        order.setRestaurant(restaurant);
        order.setStatusOrder(StatusOrder.PENDENTE);

        return orderRepository.save(order);
    }

    // Adicionar item ao pedido
    public Order addItem(Long id, Long productId, Integer quantity) {
        Order order = findPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + productId));

        if (!product.isAvailable()) {
            throw new IllegalArgumentException("Produto indisponível: " + product.getName());
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        if (!product.getRestaurant().getId().equals(order.getRestaurant().getId())) {
            throw new IllegalArgumentException("Produto não pertence ao restaurante do pedido");
        }

        ItemOrder item = new ItemOrder();
        item.setOrder(order);
        item.setProducts(product);
        item.setQuantity(quantity);
        item.setUnitaryPrice(product.getPrice());
        item.calculateSubtotal();

        order.addItem(item);

        return orderRepository.save(order);
    }

    // Confirmar pedido
    public Order confirmOrder(Long id) {
        Order order = findPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        if (order.getStatusOrder() != StatusOrder.PENDENTE) {
            throw new IllegalArgumentException("Apenas pedidos pendentes podem ser confirmados");
        }

        if (order.getItemsOrder().isEmpty()) {
            throw new IllegalArgumentException("Pedido deve ter pelo menos 1 item");
        }

        order.confirm();

        order.setStatusOrder(StatusOrder.CONFIRMADO);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findPerId(Long id) {
        return orderRepository.findById(id);
    }

    // Listar pedidos p/ cliente
    @Transactional(readOnly = true)
    public List<Order> listPerClient(Long clientId) {
        return orderRepository.findByClientIdOrderByDateOrderDesc(clientId);
    }

    // Cancelar pedido
    public Order cancelOrder(Long id, String reason) {
        Order order = findPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        if (order.getStatusOrder() == StatusOrder.ENTREGUE) {
            throw new IllegalArgumentException("Pedido já entregue, não pode ser cancelado");
        }

        if (order.getStatusOrder() == StatusOrder.CANCELADO) {
            throw new IllegalArgumentException("Pedido já está cancelado");
        }

        order.setStatusOrder(StatusOrder.CANCELADO);

        if (reason != null && !reason.trim().isEmpty()) {
            order.setNote(order.getNote() + " | Cancelado: " + reason);
        }

        return orderRepository.save(order);
    }

    public Order updateStatusOrder(Long id, StatusOrder statusOrder) {
        throw new UnsupportedOperationException("Método não implementado 'updateStatusOrder' (Atualizar status do pedido).");
    }
}
