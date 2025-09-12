package com.deliverytech.delivery_api.data.response;

import com.deliverytech.delivery_api.data.request.SalesByRestaurantDTORequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Sales summary for a period")
public class SalesSummaryDTOResponse {
@Schema(description = "Total sales amount", example = "15000.00")
    private BigDecimal totalSales;

    @Schema(description = "Number of orders in the period", example = "300")
    private long numberOfOrders;

    @Schema(description = "Sales breakdown by restaurant")
    private List<SalesByRestaurantDTORequest> salesByRestaurant;

    // Getters and Setters
    public BigDecimal getTotalSales() { return totalSales; }
    public void setTotalSales(BigDecimal totalSales) { this.totalSales = totalSales; }

    public long getNumberOfOrders() { return numberOfOrders; }
    public void setNumberOfOrders(long numberOfOrders) { this.numberOfOrders = numberOfOrders; }

    public List<SalesByRestaurantDTORequest> getSalesByRestaurant() { return salesByRestaurant; }
    public void setSalesByRestaurant(List<SalesByRestaurantDTORequest> salesByRestaurant) { this.salesByRestaurant = salesByRestaurant; }
}