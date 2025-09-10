package com.deliverytech.delivery_api.data.response;

import com.deliverytech.delivery_api.data.ItemRequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.math.BigDecimal;

public class CalculateRequestDTOResponse {
    @Schema(description = "Subtotal request amount in R$", example = "59.90")
    private BigDecimal subtotal;

    @Schema(description = "Total request amount in R$", example = "59.90")
    private BigDecimal total;

    @Schema(description = "Delivery fee in R$", example = "5.50")
    private BigDecimal deliveryFee;

    @Schema(description = "List of request items with subtotals")
    private List<ItemRequestDTO> items;

     // Getters and Setters
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(BigDecimal deliveryFee) { this.deliveryFee = deliveryFee; }

    public List<ItemRequestDTO> getItems() { return items; }
    public void setItems(List<ItemRequestDTO> items) { this.items = items; }
}