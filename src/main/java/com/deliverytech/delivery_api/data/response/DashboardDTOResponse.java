package com.deliverytech.delivery_api.data.response;

import com.deliverytech.delivery_api.model.Request;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Dashboard statistics")
public class DashboardDTOResponse {
@Schema(description = "Total number of orders", example = "1000")
    private List<Request> totalRequests;

    @Schema(description = "Total revenue", example = "50000.00")
    private BigDecimal totalRevenue;

    @Schema(description = "Number of active restaurants", example = "50")
    private long activeRestaurants;

    @Schema(description = "Number of registered customers", example = "2000")
    private long registeredCustomers;

    // Getters and Setters
    public List<Request> getTotalRequests() { return totalRequests; }
    public void setTotalRequests(List<Request> totalRequests) { this.totalRequests = totalRequests; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public long getActiveRestaurants() { return activeRestaurants; }
    public void setActiveRestaurants(long activeRestaurants) { this.activeRestaurants = activeRestaurants; }

    public long getRegisteredCustomers() { return registeredCustomers; }
    public void setRegisteredCustomers(long registeredCustomers) { this.registeredCustomers = registeredCustomers; }
}