package com.deliverytech.delivery_api.data.request;

import com.deliverytech.delivery_api.data.ItemRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public class RequestDTORequest {
    @NotNull(message = "Cliente é obrigatório")
    private Long clientId;

    @NotNull(message = "Restaurante é obrigatório")
    private Long restaurantId;

    @NotBlank(message = "Endereço de entrega é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máx. 200 caracteres")
    private String deliveryAddress;

    @NotEmpty(message = "Pedido deve ter pelo menos 1 item")
    @Valid
    private List <ItemRequestDTO> items;

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public String getAddressDelivery() { return deliveryAddress; }
    public void setClientId(String deliveryAddres) { this.deliveryAddress = deliveryAddres; }

    public List<ItemRequestDTO> getItems() { return items; }
    public void setItems(List items) { this.items = items; }
}
