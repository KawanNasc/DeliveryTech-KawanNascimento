package com.deliverytech.delivery_api.services.interfaces;

import com.deliverytech.delivery_api.data.request.ProductDTORequest;
import com.deliverytech.delivery_api.data.response.ProductDTOResponse;

import java.util.List;

public interface ProductServiceInterface {
    ProductDTOResponse registerProduct(ProductDTORequest dto);

    ProductDTOResponse findProductById(Long id);

    ProductDTOResponse updateProduct(Long id, ProductDTORequest dto);

    void removeProduct(Long id);

    ProductDTOResponse changeAvailability(Long id);

    List<ProductDTOResponse> findProductsByCategory(String category);

    List<ProductDTOResponse> searchProductsByName(String name);

    List<ProductDTOResponse> findProductsByRestaurant(Long restaurantId, Boolean available);
}