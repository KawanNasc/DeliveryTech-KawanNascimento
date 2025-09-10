package com.deliverytech.delivery_api.data.request;

import com.deliverytech.delivery_api.data.ItemRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RequestDTORequest {
    @NotBlank(message = "Data da entrega é obrigatório")
    private LocalDateTime dateRequest;
    
    @Size(max = 200, message = "Anotação deve ter no máx. 200 caracteres")
    private String note;

    @NotBlank(message = "Endereço de entrega é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máx. 200 caracteres")
    private String deliveryAddress;

    @NotBlank(message = "Subtotal é obrigatório")
    private BigDecimal subtotal;

    @NotBlank(message = "Taxa de entrega é obrigatório")
    private BigDecimal deliveryFee;

    @NotBlank(message = "Tempo da entrega é obrigatório")
    private Integer deliveryTime;

    @NotBlank(message = "Horário de trabalho é obrigatório")
    private String workHours;

    @NotBlank(message = "Valor total é obrigatório")
    private BigDecimal totalValue;

    @NotNull(message = "Cliente é obrigatório")
    private Long clientId;
    
    @NotNull(message = "Restaurante é obrigatório")
    private Long restaurantId;
    
    @NotEmpty(message = "Pedido deve ter pelo menos 1 item")
    @Valid
    private List<ItemRequestDTO> items;
    
    public LocalDateTime getDateRequest() { return dateRequest; }
    public void setDateRequest(LocalDateTime dateRequest) { this.dateRequest = dateRequest; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(BigDecimal deliveryFee) { this.deliveryFee = deliveryFee; }

    public Integer getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(Integer deliveryTime) { this.deliveryTime = deliveryTime; }

    public String getWorkHours() { return workHours; }
    public void setWorkHours(String workHours) { this.workHours = workHours; }

    public BigDecimal getTotalValue() { return totalValue; }
    public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public List<ItemRequestDTO> getItems() { return items; }
    public void setItems(List<ItemRequestDTO> items) { this.items = items; }
}