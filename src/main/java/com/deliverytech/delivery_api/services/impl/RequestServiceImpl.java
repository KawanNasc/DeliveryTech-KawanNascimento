package com.deliverytech.delivery_api.services.impl;

import com.deliverytech.delivery_api.data.request.RequestDTORequest;
import com.deliverytech.delivery_api.data.response.RequestDTOResponse;
import com.deliverytech.delivery_api.data.ItemRequestDTO;

import com.deliverytech.delivery_api.model.*;

import com.deliverytech.delivery_api.enums.StatusRequest;
import com.deliverytech.delivery_api.services.interfaces.RequestServiceInterface;

import com.deliverytech.delivery_api.exception.BusinessException;
import com.deliverytech.delivery_api.exception.EntityNotFoundException;

import com.deliverytech.delivery_api.repository.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RequestServiceImpl implements RequestServiceInterface {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public RequestDTOResponse createRequest(RequestDTORequest dto) {
        // 1. Validar se cliente existe e está ativo
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        if (!client.isActive()) {
            throw new BusinessException("Clientes inativos não pode fazer pedidos");
        }

        // 2. Validar se restaurante existe e está ativo
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));

        if (!restaurant.isActive()) {
            throw new BusinessException("Restaurante não está disponível");
        }

        // 3. Validar todos os produtos existem e estão disponíveis
        List<ItemRequest> itemsRequest = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (ItemRequestDTO itemRequestDTO : dto.getItems()) {
            Product product = productRepository.findById(itemRequestDTO.getProductId()).orElseThrow(
                    () -> new EntityNotFoundException("Produto não encontrado: " + itemRequestDTO.getProductId()));

            if (!product.isAvailable()) {
                throw new BusinessException("Produto indisponível: " + product.getName());
            }

            if (!product.getRestaurant().getId().equals(dto.getRestaurantId())) {
                throw new BusinessException("Produto não pertence ao restaurante selecionado");
            }

            // Criar item do pedido
            ItemRequest item = new ItemRequest();
            item.setProducts(product);
            item.setQuantity(itemRequestDTO.getQuantity());
            item.setUnitaryPrice(product.getPrice());
            item.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(itemRequestDTO.getQuantity())));

            itemsRequest.add(item);
            subtotal = subtotal.add(item.getSubtotal());
        }

        // 4. Calcular total do pedido
        BigDecimal deliveryFee = restaurant.getDeliveryFee();
        BigDecimal totalValue = subtotal.add(deliveryFee);

        // 5. Salvar pedido
        Request request = new Request();
        request.setClient(client);
        request.setRestaurant(restaurant);
        request.setDateRequest(LocalDateTime.now());
        request.setStatusRequest(StatusRequest.PENDENTE);
        request.setSubtotal(subtotal);
        request.setDeliveryFee(deliveryFee);
        request.setTotalValue(totalValue);

        Request savedRequest = requestRepository.save(request);

        // 6. Salvar itens do pedido
        for (ItemRequest item : itemsRequest) {
            item.setRequest(savedRequest);
        }

        savedRequest.setItemsRequest(itemsRequest);

        // 7. Atualizar estoque (Se aplicável) - Simulação
        // Em um cenário real, aqui seria decrementado o estoque

        // 8. Montar resposta manualmente
        RequestDTOResponse response = new RequestDTOResponse();
        response.setId(savedRequest.getId());
        response.setClientId(savedRequest.getClient().getId());
        response.setRestaurantId(savedRequest.getRestaurant().getId());
        response.setDeliveryAddress(savedRequest.getDeliveryAddress());
        response.setNote(savedRequest.getNote());

        // 9. Mapear itens para DTO
        List<ItemRequestDTO> itemDTOs = savedRequest.getItemsRequest().stream()
            .map(item -> {
                ItemRequestDTO dtoItem = new ItemRequestDTO();
                dtoItem.setProductId(item.getProducts().getId());
                dtoItem.setQuantity(item.getQuantity());
                return dtoItem;
            })
            .collect(Collectors.toList());

        response.setItems(itemDTOs);
            
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public RequestDTOResponse findRequestPerId(Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));

        return modelMapper.map(request, RequestDTOResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDTOResponse> findRequestsPerClient(Long clientId) {
        List<Request> requests = requestRepository.findByClientIdOrderByDateRequestDesc(clientId);

        return requests.stream().map(request -> modelMapper.map(request, RequestDTOResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public RequestDTOResponse updateStatusRequesT(Long id, StatusRequest newStatus) {
         Request request = requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
    
        // Validar transições de status permitidas
        if (!isValidTransiction(request.getStatusRequest(), newStatus)) {
            throw new BusinessException("Transição de status inválida: " + request.getStatusRequest() + " -> " + newStatus);
        }
    
        request.setStatusRequest(newStatus);
        Request updatedRequest = requestRepository.save(request);
    
        return modelMapper.map(updatedRequest, RequestDTOResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalRequest(List<ItemRequestDTO> items) {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemRequestDTO item : items) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

            BigDecimal subtotalItem = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(subtotalItem);
        }

        return total;
    }

    @Override
    public void cancelRequest(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        if (!canBeCanceled(request.getStatusRequest())) {
            throw new BusinessException("Pedido não pode ser cancelado no status: " + request.getStatusRequest());
        }

        request.setStatusRequest(StatusRequest.CANCELADO);
        requestRepository.save(request);
    }

    private boolean isValidTransiction(StatusRequest nowStatus, StatusRequest newStatus) {
        // Implementar lógica de transições válidas
        switch(nowStatus) {
            case PENDENTE:
                return newStatus == StatusRequest.CONFIRMADO || newStatus == StatusRequest.CANCELADO;
            case CONFIRMADO:
                return newStatus == StatusRequest.PREPARANDO || newStatus == StatusRequest.CANCELADO;
            case PREPARANDO:
                return newStatus == StatusRequest.SAIU_PARA_ENTREGA;
            case SAIU_PARA_ENTREGA:
                return newStatus == StatusRequest.ENTREGUE;
            default:
                return false;
        }
    }

    private boolean canBeCanceled(StatusRequest statusRequest) {
        return statusRequest == StatusRequest.PENDENTE || statusRequest == StatusRequest.CONFIRMADO;
    }
}
