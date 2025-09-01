package com.deliverytech.delivery_api.data;

import jakarta.validation.constraints.*;

public class ItemRequestDTO {
    @NotNull(message = "Produto é obrigatório")
    private Long productId;

    @NotNull(message = "Quantidade é obrigatório")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    @Max(value = 10, message = "Quantidade máxima é 10")
    private Integer quantity;

    // Getters e Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
