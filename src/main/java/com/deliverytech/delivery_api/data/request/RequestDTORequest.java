package com.deliverytech.delivery_api.data.request;

import com.deliverytech.delivery_api.data.ItemRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import com.deliverytech.delivery_api.validation.valid.CEPValid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RequestDTORequest {
    // @NotNull(message = "Data da entrega é obrigatório")
    private LocalDateTime dateRequest;
    
    @Size(max = 500, message = "Anotação deve ter no máx. 500 caracteres")
    private String note;

    @NotBlank(message = "Forma de pagamento é obrigatória")
    @Pattern(regexp = "^(DINHEIRO|CARTAO_CREDITO|CARTAO_DEBITO|PIX)$", 
        message = "Forma de pagamento deve ser em dinheiro, cartão de crédito, débito ou PIX")
    private String paymentWay;

    @NotBlank(message = "Endereço de entrega é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máx. 200 caracteres")
    private String deliveryAddress;

    @NotBlank(message = "CEP é obrigatório")
    @CEPValid
    private String cep;

    @NotNull(message = "Subtotal é obrigatório")
    @DecimalMin(value = "0.00", inclusive = false, message = "Subtotal deve ser positivo")
    private BigDecimal subtotal;

    @NotNull(message = "Taxa de entrega é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "Taxa de entrega deve ser positiva ou zero")
     private BigDecimal deliveryFee;

    @NotNull(message = "Tempo da entrega é obrigatório")
    @Min(value = 1, message = "Tempo da entrega deve ser maior que 0")
    private Integer deliveryTime;

    @NotBlank(message = "Horário de trabalho é obrigatório")
    private String workHours;

    @NotNull(message = "Valor total é obrigatório")
    @DecimalMin(value = "0.00", inclusive = false, message = "Valor total deve ser positivo")
    private BigDecimal totalValue;

    @NotNull(message = "Cliente é obrigatório")
    @Positive(message = "ID do cliente deve ser positivo")
    private Long clientId;
    
    @NotNull(message = "Restaurante é obrigatório")
    @Positive(message = "ID do restaurante deve ser positivo")
    private Long restaurantId;
    
    @NotEmpty(message = "Pedido deve ter pelo menos 1 item")
    @Valid
    private List<ItemRequestDTO> items;
    
    public LocalDateTime getDateRequest() { return dateRequest; }
    public void setDateRequest(LocalDateTime dateRequest) { this.dateRequest = dateRequest; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getPaymentWay() { return paymentWay; }
    public void setPaymentWay(String paymentWay) { this.paymentWay = paymentWay; }
    
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

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