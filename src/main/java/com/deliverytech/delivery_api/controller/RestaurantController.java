package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.data.*;

import com.deliverytech.delivery_api.services.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

// @RestController
// @RequestMapping("/restaurant")
// @CrossOrigin(origins = "*")
// @Tag(name = "Restaurants", description = "Operations related to restaurants")
// public class RestaurantController {
//     @Autowired
//     private RestaurantService restaurantService;

//     @PostMapping
//     @Operation(summary = "Register restaurant", description = "Create a new restaurant in the system")
//     @ApiResponses({
//         @ApiResponse(responseCode = "201", description = "Restaurant created with success"),
//         @ApiResponse(responseCode = "400", description = "Invalid data"),
//         @ApiResponse(responseCode = "409", description = "Restaurant exists")
//     })
//     public ResponseEntity<ApiResponseWrapper<RestaurantRs
// }
