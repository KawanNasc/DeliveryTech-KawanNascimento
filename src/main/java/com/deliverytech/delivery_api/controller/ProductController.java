package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.config.ApiResponseWrapper;

import com.deliverytech.delivery_api.data.request.ProductDTORequest;
import com.deliverytech.delivery_api.data.response.ProductDTOResponse;

import com.deliverytech.delivery_api.services.interfaces.ProductServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {
        @Autowired
        private ProductServiceInterface productServiceInt;

        @PostMapping
        @Operation(summary = "Register product", description = "Creates a new product in the system")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Product created successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid data"),
                @ApiResponse(responseCode = "404", description = "Restaurant not found")
        })
        public ResponseEntity<ApiResponseWrapper<ProductDTOResponse>> register(@Valid @RequestBody
                @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data of the product to be created")
                ProductDTORequest dto) {
                        ProductDTOResponse product = productServiceInt.registerProduct(dto);
                        ApiResponseWrapper<ProductDTOResponse> response = new ApiResponseWrapper<>(true, product,
                                        "Product created successfully");
                        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Find product by ID", description = "Retrieves a specific product by ID")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Product found"),
                @ApiResponse(responseCode = "404", description = "Product not found")
        })
        public ResponseEntity<ApiResponseWrapper<ProductDTOResponse>> findById(
                @Parameter(description = "Product ID") @PathVariable Long id) {
                ProductDTOResponse product = productServiceInt.findProductById(id);
                ApiResponseWrapper<ProductDTOResponse> response = new ApiResponseWrapper<>(true, product, "Product found");
                return ResponseEntity.ok(response);
        }

        @PutMapping("/{id}")
        @Operation(summary = "Update product", description = "Updates the data of an existing product")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Product updated successfully"),
                @ApiResponse(responseCode = "404", description = "Product not found"),
                @ApiResponse(responseCode = "400", description = "Invalid data")
        })
        public ResponseEntity<ApiResponseWrapper<ProductDTOResponse>> update(
                        @Parameter(description = "Product ID") @PathVariable Long id,
                        @Valid @RequestBody ProductDTORequest dto) {
                ProductDTOResponse product = productServiceInt.updateProduct(id, dto);
                ApiResponseWrapper<ProductDTOResponse> response = new ApiResponseWrapper<>(true, product, "Product updated successfully");
                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Remove product", description = "Removes a product from the system")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Product removed successfully"),
                @ApiResponse(responseCode = "404", description = "Product not found"),
                @ApiResponse(responseCode = "409", description = "Product has associated requests")
        })

        public ResponseEntity<Void> remove(@Parameter(description = "Product ID") @PathVariable Long id) {
                productServiceInt.removeProduct(id);
                return ResponseEntity.noContent().build();
        }

        @PatchMapping("/{id}/availability")
        @Operation(summary = "Change availability", description = "Toggles the product availability")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Availability changed successfully"),
                @ApiResponse(responseCode = "404", description = "Product not found")
        })

        public ResponseEntity<ApiResponseWrapper<ProductDTOResponse>> changeAvailability(
                        @Parameter(description = "Product ID") @PathVariable Long id) {
                ProductDTOResponse product = productServiceInt.changeAvailability(id);
                ApiResponseWrapper<ProductDTOResponse> response = new ApiResponseWrapper<>(true, product,
                                "Availability changed successfully");
                return ResponseEntity.ok(response);
        }

        @GetMapping("/category/{category}")
        @Operation(summary = "Find by category", description = "Lists products of a specific category")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Products found")
        })

        public ResponseEntity<ApiResponseWrapper<List<ProductDTOResponse>>> findByCategory(
                        @Parameter(description = "Product category") @PathVariable String category) {
                List<ProductDTOResponse> products = productServiceInt.findProductsByCategory(category);
                ApiResponseWrapper<List<ProductDTOResponse>> response = new ApiResponseWrapper<>(true, products, "Products found");
                return ResponseEntity.ok(response);
        }

        @GetMapping("/search")
        @Operation(summary = "Search by name", description = "Searches products by name")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Search performed successfully")
        })
        public ResponseEntity<ApiResponseWrapper<List<ProductDTOResponse>>> searchByName(
                        @Parameter(description = "Product name") @RequestParam String name) {
                List<ProductDTOResponse> products = productServiceInt.searchProductsByName(name);
                ApiResponseWrapper<List<ProductDTOResponse>> response = new ApiResponseWrapper<>(true, products, "Search performed successfully");
                return ResponseEntity.ok(response);
        }
}