package com.deliverytech.delivery_api.data.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Sales details for a specific restaurant")
public class SalesByRestaurantDTORequest {
  @Schema(description = "Restaurant name", example = "Pizza Express")
    private String restaurantName;

    @Schema(description = "Total sales for this restaurant", example = "5000.00")
    private BigDecimal total;

    // Getters and Setters
    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}