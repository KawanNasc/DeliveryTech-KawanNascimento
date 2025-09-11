package com.deliverytech.delivery_api.services.impl;

import com.deliverytech.delivery_api.data.request.RestaurantDTORequest;
import com.deliverytech.delivery_api.data.response.RestaurantDTOResponse;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.RestaurantRepository;
import com.deliverytech.delivery_api.services.interfaces.RestaurantServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantServiceInterface {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @Transactional
    public RestaurantDTOResponse register(RestaurantDTORequest dto) {
        // Check if restaurant with the same name and address already exists
        if (restaurantRepository.existsByNameAndAddress(dto.getName(), dto.getAddress())) {
            throw new IllegalArgumentException("Restaurant already exists");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setCategory(dto.getCategory());
        restaurant.setAddress(dto.getAddress());
        restaurant.setPhone(dto.getPhone());
        restaurant.setDeliveryFee(dto.getDeliveryFee());
        restaurant.setDeliveryTime(dto.getDeliveryTime());
        restaurant.setWorkHours(dto.getWorkHours());
        restaurant.setEvaluation(dto.getEvaluation());
        restaurant.setActive(true);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return toDTOResponse(savedRestaurant);
    }

    @Override
    public Page<RestaurantDTOResponse> listActive(String category, Pageable pageable) {
        Page<Restaurant> restaurants;
        if (category != null && !category.isBlank()) {
            restaurants = restaurantRepository.findByCategoryAndActiveTrue(category, pageable);
        } else {
            restaurants = restaurantRepository.findByActiveTrue(pageable);
        }
        return restaurants.map(this::toDTOResponse);
    }

    @Override
    public RestaurantDTOResponse findRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        return toDTOResponse(restaurant);
    }

    @Override
    public List<RestaurantDTOResponse> findRestaurantsByCategory(String category) {
        List<Restaurant> restaurants = restaurantRepository.findByCategory(category);
        return restaurants.stream().map(this::toDTOResponse).collect(Collectors.toList());
    }

    @Override
    public BigDecimal calculateDeliveryFee(Long id, String zip) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        // Simplified delivery fee calculation: base fee + additional based on ZIP
        // In a real system, this would involve geolocation or ZIP code-based logic
        BigDecimal baseFee = restaurant.getDeliveryFee();
        BigDecimal additionalFee = calculateAdditionalFee(zip); // Hypothetical method
        return baseFee.add(additionalFee);
    }

    @Override
    public List<RestaurantDTOResponse> findNearbyRestaurants(String zip, Integer radius) {
        // Simplified logic: Assume a method to filter restaurants by ZIP code proximity
        // In a real system, this would use geolocation services or a ZIP code database
        List<Restaurant> restaurants = restaurantRepository.findAll().stream()
                .filter(restaurant -> isWithinRadius(restaurant.getAddress(), zip, radius)) // Hypothetical method
                .collect(Collectors.toList());
        return restaurants.stream().map(this::toDTOResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RestaurantDTOResponse updateRestaurant(Long id, RestaurantDTORequest dto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        // Check if another restaurant with the same name and address exists
        if (restaurantRepository.existsByNameAndAddressAndIdNot(dto.getName(), dto.getAddress(), id)) {
            throw new IllegalArgumentException("Another restaurant with the same name and address exists");
        }

        restaurant.setName(dto.getName());
        restaurant.setCategory(dto.getCategory());
        restaurant.setAddress(dto.getAddress());
        restaurant.setPhone(dto.getPhone());
        restaurant.setDeliveryFee(dto.getDeliveryFee());
        restaurant.setDeliveryTime(dto.getDeliveryTime());
        restaurant.setWorkHours(dto.getWorkHours());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return toDTOResponse(updatedRestaurant);
    }

    @Override
    @Transactional
    public RestaurantDTOResponse changeRestaurantStatus(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        restaurant.setActive(!restaurant.isActive());
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return toDTOResponse(updatedRestaurant);
    }

    // Helper method to convert Restaurant entity to RestaurantDTOResponse
    private RestaurantDTOResponse toDTOResponse(Restaurant restaurant) {
        RestaurantDTOResponse dto = new RestaurantDTOResponse();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setCategory(restaurant.getCategory());
        dto.setAddress(restaurant.getAddress());
        dto.setPhone(restaurant.getPhone());
        dto.setDeliveryFee(restaurant.getDeliveryFee());
        dto.setDeliveryTime(restaurant.getDeliveryTime());
        dto.setWorkHours(restaurant.getWorkHours());
        dto.setZip(restaurant.getZip());
        dto.setEvaluation(restaurant.getEvaluation());
        dto.setActive(restaurant.isActive());
        return dto;
    }

    // Hypothetical method to calculate additional fee based on ZIP code
    private BigDecimal calculateAdditionalFee(String zip) {
        // Placeholder logic: In a real system, use a geolocation service or ZIP code database
        return new BigDecimal("2.00"); // Example additional fee
    }

    // Hypothetical method to check if restaurant is within radius of ZIP code
    private boolean isWithinRadius(String restaurantAddress, String zip, Integer radius) {
        // Placeholder logic: In a real system, use a geolocation service
        return true; // Assume all restaurants are within radius for simplicity
    }
}