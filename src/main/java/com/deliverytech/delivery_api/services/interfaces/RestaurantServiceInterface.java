package com.deliverytech.delivery_api.services.interfaces;

import com.deliverytech.delivery_api.data.request.RestaurantDTORequest;
import com.deliverytech.delivery_api.data.response.RestaurantDTOResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantServiceInterface {
    RestaurantDTOResponse register(RestaurantDTORequest dto);

    Page<RestaurantDTOResponse> listActive(String category, boolean active, Pageable pageable);

    RestaurantDTOResponse findRestaurantById(Long id);

    RestaurantDTOResponse updateRestaurant(Long id, RestaurantDTORequest dto);

    RestaurantDTOResponse changeRestaurantStatus(Long id);

    List<RestaurantDTOResponse> findRestaurantsByCategory(String category);

    List<RestaurantDTOResponse> findNearbyRestaurants(String zip, Integer radius);

    BigDecimal calculateDeliveryFee(Long id, String zip);

}