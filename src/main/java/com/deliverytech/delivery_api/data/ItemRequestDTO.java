package com.deliverytech.delivery_api.data;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ItemRequestDTO {
    @NotNull(message = "Quantidade é obrigatório")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    @Max(value = 10, message = "Quantidade máxima é 10")
    private Integer quantity;
    
    @NotBlank(message = "Valor unitário é obrigatório")
    private BigDecimal unitaryPrice;

    @NotBlank(message = "Subtotal é obrigatório")
    private BigDecimal subtotal;

    @NotNull(message = "Produto é obrigatório")
    private Long productId;

    // Getters e Setters
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitaryPrice() { return unitaryPrice; }
    public void setUnitaryPrice(BigDecimal unitaryPrice) { this.unitaryPrice = unitaryPrice; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}