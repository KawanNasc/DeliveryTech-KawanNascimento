package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.data.response.DashboardDTOResponse;
import com.deliverytech.delivery_api.data.response.SalesSummaryDTOResponse;
import com.deliverytech.delivery_api.services.RequestService;
import com.deliverytech.delivery_api.services.RestaurantService;
import com.deliverytech.delivery_api.services.UserService;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;


import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.http.ResponseEntity;

import com.deliverytech.delivery_api.config.ApiResponseWrapper;

import java.time.LocalDateTime;

// Controller with multiple tags 
@RestController
@RequestMapping("/api/admin")
@Tags({
        @Tag(name = "Administration", description = "Exclusive endpoints for administrators"),
        @Tag(name = "Reports", description = "Generation of reports and statistics")
})
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    @Operation(summary = "Administrative dashboard", description = "Returns general system statistics for the dashboard", tags = {
            "Administration", "Reports" })
    public ResponseEntity<ApiResponseWrapper<DashboardDTOResponse>> getDashboard() {
        DashboardDTOResponse dto = new DashboardDTOResponse();
        dto.setTotalRequests(requestService.listAll());
        dto.setTotalRevenue(requestService.getTotalRevenue());
        dto.setActiveRestaurants(restaurantService.countActiveRestaurants());
        dto.setRegisteredCustomers(userService.listAll());

        ApiResponseWrapper<DashboardDTOResponse> response = new ApiResponseWrapper<>(true, dto,
                "Dashboard statistics retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard/sells")
    @Operation(summary = "Sales report", description = "Generates detailed sales report for the period", tags = {
            "Reports" })
    public ResponseEntity<ApiResponseWrapper<SalesSummaryDTOResponse>> getSummarySells(
            @Parameter(description = "Start date") @RequestParam LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam LocalDateTime endDate,
            @Parameter(description = "Pagination parameters") Pageable pageable) {
        SalesSummaryDTOResponse dto = new SalesSummaryDTOResponse();
        dto.setTotalSales(requestService.getTotalSalesBetween(startDate, endDate, pageable));
        dto.setNumberOfOrders(requestService.countRequestsBetween(startDate, endDate));
        dto.setSalesByRestaurant(requestService.getSalesByRestaurantBetween(startDate, endDate, pageable));

        ApiResponseWrapper<SalesSummaryDTOResponse> response = new ApiResponseWrapper<>(true, dto,
                "Sales summary retrieved successfully");

        return ResponseEntity.ok(response);
    }
}