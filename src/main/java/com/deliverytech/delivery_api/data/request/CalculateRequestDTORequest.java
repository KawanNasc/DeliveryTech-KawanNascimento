package com.deliverytech.delivery_api.data.request;

import com.deliverytech.delivery_api.data.ItemRequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.util.List;

@Schema(description = "Data for calculating an request")
public class CalculateRequestDTORequest {

    @Schema(description = "Restaurant's unique identifier", example = "1")
    @NotNull(message = "ID do restaurante é obrigatório")
    @Positive(message = "ID do restaurante deve ser positivo")
    private Long restaurantId;

    @Schema(description = "List of request items")
    @NotEmpty(message = "A lista de itens não pode estar vazia")
    @Size(min = 1, message = "Deve haver pelo menos um item no pedido")
    private List<ItemRequestDTO> items;

    @Schema(description = "Delivery ZIP code", example = "12345678")
    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve ter 8 dígitos")
    private String deliveryZip;

    // Getters and Setters
    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public List<ItemRequestDTO> getItems() { return items; }
    public void setItems(List<ItemRequestDTO> items) { this.items = items; }

    public String getDeliveryZip() { return deliveryZip; }
    public void setDeliveryZip(String deliveryZip) { this.deliveryZip = deliveryZip; }
}
