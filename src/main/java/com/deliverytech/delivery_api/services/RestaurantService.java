package com.deliverytech.delivery_api.services;

import com.deliverytech.delivery_api.model.Restaurant;

import com.deliverytech.delivery_api.repository.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Cadastrar nbvo estaurante
    public Restaurant register(Restaurant restaurant) {

        // Validar nome único
        if (restaurantRepository.findByName(restaurant.getName()).isPresent()) {
            throw new IllegalArgumentException("Restaurante já cadastrado: " + restaurant.getName());
        }

        validateDataRestaurant(restaurant);
        restaurant.setActive(true);

        return restaurantRepository.save(restaurant);
    }

    // Buscar por ID
    @Transactional(readOnly = true)
    public Optional<Restaurant> findPerId(Long id) {
        return restaurantRepository.findById(id);
    }

    // Listar restaurantes ativos
    @Transactional(readOnly = true)
    public Page<Restaurant> listActive(Pageable pageable) {
        return restaurantRepository.findByActiveTrue(pageable);
    }

    // Buscar p/ categoria
    @Transactional(readOnly = true)
    public Page<Restaurant> findPerCategory(String category, Pageable pageable) {
        return restaurantRepository.findByCategoryAndActiveTrue(category, pageable);
    }

    // Atualizar restaurante
    public Restaurant update(Long id, Restaurant updatedRestaurant) {
        Restaurant restaurant = findPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        // Veriricar nome único (Se mudou)
        if (!restaurant.getName().equals(updatedRestaurant.getName())
                && restaurantRepository.findByName(updatedRestaurant.getName()).isPresent()) {
            throw new IllegalArgumentException("Nome já cadastrado: " + updatedRestaurant.getName());
        }

        restaurant.setName(updatedRestaurant.getName());
        restaurant.setCategory(updatedRestaurant.getCategory());
        restaurant.setAddress(updatedRestaurant.getAddress());
        restaurant.setPhone(updatedRestaurant.getPhone());
        restaurant.setDeliveryFee(updatedRestaurant.getDeliveryFee());

        return restaurantRepository.save(restaurant);
    }

    // Inativar restaurante
    public void inative(Long id) {
        Restaurant restaurant = findPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        restaurant.setActive(false);
        restaurantRepository.save(restaurant);
    }

    private void validateDataRestaurant(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (restaurant.getDeliveryFee() != null || restaurant.getDeliveryFee().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de entrega não pode ser negativa");
        }
    }
}
