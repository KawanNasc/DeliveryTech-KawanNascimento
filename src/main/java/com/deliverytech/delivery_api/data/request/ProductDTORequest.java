package com.deliverytech.delivery_api.data.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "Data for product creation or update request")
public class ProductDTORequest {

    @Schema(description = "Product's name", example = "Margherita Pizza")
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2-100 caracteres")
    private String name;

    @Schema(description = "Product's description", example = "Classic pizza with tomato sauce, mozzarella, and basil")
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String description;

    @Schema(description = "Product's price in R$", example = "29.90", minimum = "0")
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", message = "Preço deve ser positivo")
    private BigDecimal price;

    @Schema(description = "Product's category", example = "Pizza", allowableValues = {"Pizza", "Bebida", "Sobremesa", "Lanche", "Prato"})
    @NotBlank(message = "Categoria é obrigatória")
    private String category;

    @Schema(description = "Product availability", example = "true")
    @NotNull(message = "Disponibilidade é obrigatória")
    private Boolean available;

    @Schema(description = "Restaurant's unique identifier", example = "1")
    @NotNull(message = "ID do restaurante é obrigatório")
    @Positive(message = "ID do restaurante deve ser positivo")
    private Long restaurantId;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }
}
