package com.deliverytech.delivery_api.data;

import java.math.BigDecimal;

public interface SellsDTO {
    String getNameRestaurant();
    BigDecimal getTotalSells();
    Long getQuantityRequests();
}
