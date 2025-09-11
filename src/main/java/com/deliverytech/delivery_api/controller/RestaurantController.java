package com.deliverytech.delivery_api.controller;

// import com.deliverytech.delivery_api.config.ApiResponseWrapper;
// import com.deliverytech.delivery_api.config.PagedResponseWrapper;

import com.deliverytech.delivery_api.exception.EntityNotFoundException;
import com.deliverytech.delivery_api.exception.ConflictException;

import com.deliverytech.delivery_api.data.request.RestaurantDTORequest;
// import com.deliverytech.delivery_api.data.response.RestaurantDTOResponse;
// import com.deliverytech.delivery_api.data.response.ProductDTOResponse;

// import com.deliverytech.delivery_api.services.interfaces.RestaurantServiceInterface;
// import com.deliverytech.delivery_api.services.interfaces.ProductServiceInterface;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
// import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "Restaurants", description = "Operations related to restaurants")
public class RestaurantController {
        // @Autowired
        // private RestaurantServiceInterface restaurantServiceInt;

        // @Autowired
        // private ProductServiceInterface productServiceInt;

        private List<RestaurantDTORequest> restaurants = new ArrayList<>();

        // Versão de validações personalizadas
        @PostMapping
        public ResponseEntity<RestaurantDTORequest> create(
                        @Valid @RequestBody RestaurantDTORequest restaurantDTORequest) {
                boolean nameExists = restaurants.stream()
                                .anyMatch(r -> r.getName().equalsIgnoreCase(restaurantDTORequest.getName()));

                if (nameExists) {
                        throw new ConflictException(
                                        "Já existe um restaurante com esse nome",
                                        "nome",
                                        restaurantDTORequest.getName());
                }

                restaurants.add(restaurantDTORequest);
                return ResponseEntity.status(HttpStatus.CREATED).body(restaurantDTORequest);
        }

        @GetMapping
        public ResponseEntity<List<RestaurantDTORequest>> listAll() {
                return ResponseEntity.ok(restaurants);
        }

        @GetMapping("/{id}")
        public ResponseEntity<RestaurantDTORequest> findPerId(
                        @PathVariable @Positive(message = "ID deve ser positivo") Long id) {
                // Apenas simulação de busca - Aplicação real seria p/ID
                if (id > restaurants.size()) {
                        throw new EntityNotFoundException("Restaurant", id);
                }

                return ResponseEntity.ok(restaurants.get(0));
        }

        @PutMapping("/{id}")
        public ResponseEntity<RestaurantDTORequest> update(
                @PathVariable @Positive(message = "ID deve ser positivo") Long id,
                @Valid @RequestBody RestaurantDTORequest restaurantDTORequest
        ) {
                if (id > restaurants.size()) {
                        throw new EntityNotFoundException("Restaurant", id);
                }
                
                // Apenas simulação de atualização
                restaurants.set(0, restaurantDTORequest);
                return ResponseEntity.ok(restaurantDTORequest);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(
                @PathVariable @Positive(message = "ID deve ser positivo") Long id
        ) {
                if (id > restaurants.size()) {
                        throw new EntityNotFoundException("Restaurant", id);
                }

                restaurants.remove(0);
                return ResponseEntity.noContent().build();
        }

        // Versão de execuções com API's

        // @PostMapping
        // @Operation(summary = "Register restaurant", description = "Create a new restaurant in the system")
        // @ApiResponses({
        //                 @ApiResponse(responseCode = "201", description = "Restaurant created with success"),
        //                 @ApiResponse(responseCode = "400", description = "Invalid data"),
        //                 @ApiResponse(responseCode = "409", description = "Restaurant exists")
        // })
        // public ResponseEntity<ApiResponseWrapper<RestaurantDTOResponse>> register(
        //                 @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Creating restaurant data") RestaurantDTORequest dto) {
        //         RestaurantDTOResponse restaurant = restaurantServiceInt.register(dto);
        //         ApiResponseWrapper<RestaurantDTOResponse> response = new ApiResponseWrapper<>(true, restaurant,
        //                         "Restaurant created with success");
        //         return ResponseEntity.status(HttpStatus.CREATED).body(response);
        // }

        // @GetMapping
        // @Operation(summary = "List restaurants", description = "List restaurants with optional filters and pagination")
        // @ApiResponses({
        //                 @ApiResponse(responseCode = "200", description = "Listed with success")
        // })
        // public ResponseEntity<PagedResponseWrapper<RestaurantDTOResponse>> listRestaurants(
        //                 @Parameter(description = "Restaurant category") @RequestParam(required = false) String category,
        //                 @Parameter(description = "Pagination parameters") Pageable pageable) {
        //         Page<RestaurantDTOResponse> restaurants = restaurantServiceInt.listActive(category, pageable);
        //         PagedResponseWrapper<RestaurantDTOResponse> response = new PagedResponseWrapper<>(restaurants);
        //         return ResponseEntity.ok(response);
        // }

        // @GetMapping("/{id}")
        // @Operation(summary = "Find restaurant by ID", description = "Retrieves a specific restaurant by ID")
        // @ApiResponses({
        //                 @ApiResponse(responseCode = "200", description = "Restaurant found"),
        //                 @ApiResponse(responseCode = "404", description = "Restaurant not found")
        // })
        // public ResponseEntity<ApiResponseWrapper<RestaurantDTOResponse>> findById(
        //                 @Parameter(description = "Restaurant ID") @PathVariable Long id) {
        //         RestaurantDTOResponse restaurant = restaurantServiceInt.findRestaurantById(id);
        //         ApiResponseWrapper<RestaurantDTOResponse> response = new ApiResponseWrapper<>(true, restaurant,
        //                         "Restaurant found");
        //         return ResponseEntity.ok(response);
        // }

        // @GetMapping("/category/{category}")
        // @Operation(summary = "Find by category", description = "Lists restaurants of a specific category")
        // @ApiResponses({
        //                 @ApiResponse(responseCode = "200", description = "Restaurants found")
        // })
        // public ResponseEntity<ApiResponseWrapper<List<RestaurantDTOResponse>>> findByCategory(
        //                 @Parameter(description = "Restaurant category") @PathVariable String category) {
        //         List<RestaurantDTOResponse> restaurants = restaurantServiceInt.findRestaurantsByCategory(category);
        //         ApiResponseWrapper<List<RestaurantDTOResponse>> response = new ApiResponseWrapper<>(true, restaurants,
        //                         "Restaurants found");
        //         return ResponseEntity.ok(response);
        // }

        // @GetMapping("/{id}/delivery-fee/{zip}")
        // @Operation(summary = "Calculate delivery fee", description = "Calculates the delivery fee for a specific ZIP code")
        // @ApiResponses({
        //                 @ApiResponse(responseCode = "200", description = "Fee calculated successfully"),
        //                 @ApiResponse(responseCode = "404", description = "Restaurant not found")
        // })
        // public ResponseEntity<ApiResponseWrapper<BigDecimal>> calculateDeliveryFee(
        //                 @Parameter(description = "Restaurant ID") @PathVariable Long id,
        //                 @Parameter(description = "Destination ZIP code") @PathVariable String zip) {
        //         BigDecimal fee = restaurantServiceInt.calculateDeliveryFee(id, zip);
        //         ApiResponseWrapper<BigDecimal> response = new ApiResponseWrapper<>(true, fee,
        //                         "Fee calculated successfully");
        //         return ResponseEntity.ok(response);
        // }

        // @GetMapping("/nearby/{zip}")
        // @Operation(summary = "Nearby restaurants", description = "Lists restaurants nearby a ZIP code")
        // @ApiResponses({
        //                 @ApiResponse(responseCode = "200", description = "Nearby restaurants found")
        // })
        // public ResponseEntity<ApiResponseWrapper<List<RestaurantDTOResponse>>> findNearby(
        //                 @Parameter(description = "Reference ZIP code") @PathVariable String zip,
        //                 @Parameter(description = "Radius in km") @RequestParam(defaultValue = "10") Integer radius) {
        //         List<RestaurantDTOResponse> restaurants = restaurantServiceInt.findNearbyRestaurants(zip, radius);
        //         ApiResponseWrapper<List<RestaurantDTOResponse>> response = new ApiResponseWrapper<>(true, restaurants,
        //                         "Nearby restaurants found");
        //         return ResponseEntity.ok(response);
        // }

        // @GetMapping("/{restaurantId}/products")
        // @Operation(summary = "Restaurant products", description = "Lists all products of a restaurant")
        // @ApiResponses({
        //                 @ApiResponse(responseCode = "200", description = "Products found"),
        //                 @ApiResponse(responseCode = "404", description = "Restaurant not found")
        // })
        // public ResponseEntity<ApiResponseWrapper<List<ProductDTOResponse>>> findRestaurantProducts(
        //                 @Parameter(description = "Restaurant ID") @PathVariable Long restaurantId,
        //                 @Parameter(description = "Filter only available") @RequestParam(required = false) Boolean available) {
        //         List<ProductDTOResponse> products = productServiceInt.findProductsByRestaurant(restaurantId, available);
        //         ApiResponseWrapper<List<ProductDTOResponse>> response = new ApiResponseWrapper<>(true, products,
        //                         "Products found");
        //         return ResponseEntity.ok(response);
        // }

        // @PutMapping("/{id}")
        // @Operation(summary = "Update restaurant", description = "Updates the data of an existing restaurant")
        // @ApiResponses({
        //                 @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
        //                 @ApiResponse(responseCode = "404", description = "Restaurant not found"),
        //                 @ApiResponse(responseCode = "400", description = "Invalid data")
        // })
        // public ResponseEntity<ApiResponseWrapper<RestaurantDTOResponse>> update(
        //                 @Parameter(description = "Restaurant ID") @PathVariable Long id,
        //                 @Valid @RequestBody RestaurantDTORequest dto) {
        //         RestaurantDTOResponse restaurant = restaurantServiceInt.updateRestaurant(id, dto);
        //         ApiResponseWrapper<RestaurantDTOResponse> response = new ApiResponseWrapper<>(true, restaurant,
        //                         "Restaurant updated successfully");
        //         return ResponseEntity.ok(response);
        // }

        // @PatchMapping("/{id}/status")
        // @Operation(summary = "Activate/Deactivate restaurant", description = "Toggles the active/inactive status of the restaurant")
        // @ApiResponses({
        //                 @ApiResponse(responseCode = "200", description = "Status changed successfully"),
        //                 @ApiResponse(responseCode = "404", description = "Restaurant not found")
        // })
        // public ResponseEntity<ApiResponseWrapper<RestaurantDTOResponse>> changeStatus(
        //                 @Parameter(description = "Restaurant ID") @PathVariable Long id) {
        //         RestaurantDTOResponse restaurant = restaurantServiceInt.changeRestaurantStatus(id);
        //         ApiResponseWrapper<RestaurantDTOResponse> response = new ApiResponseWrapper<>(true, restaurant,
        //                         "Status changed successfully");
        //         return ResponseEntity.ok(response);
        // }
}