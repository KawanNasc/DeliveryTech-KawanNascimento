package com.deliverytech.delivery_api.data.response;

import com.deliverytech.delivery_api.data.ItemRequestDTO;
import java.util.List;

public class RequestDTOResponse {
    private Long id;
    private Long clientId;
    private Long restaurantId;
    private String deliveryAddress;
    private String note;
    private List<ItemRequestDTO> items;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public List<ItemRequestDTO> getItems() { return items; }
    public void setItems(List<ItemRequestDTO> items) { this.items = items; }
}
