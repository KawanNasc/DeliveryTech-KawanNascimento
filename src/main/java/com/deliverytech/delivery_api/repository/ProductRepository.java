package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Product;
import com.deliverytech.delivery_api.model.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Buscar produtos p/ restaurante
    List<Product> findByRestaurantAndAvailableTrue(Restaurant restaurant);

    // Buscar produtos p/ ID do restaurante
    List<Product> findByRestaurantIdAndAvailableTrue(Long restaurantId);

    // Buscar p/ categoria
    List<Product> findByCategoryAndAvailableTrue(String category);

    // Buscar p/ nome contendo
    List<Product> findByNameContainingIgnoreCaseAndAvailableTrue(String name);

    // Buscar p/ faixa de preço
    List<Product> findByPriceBetweenAndAvailableTrue(BigDecimal priceMin, BigDecimal priceMax);

    // Buscar produtos mais baratos que um valor
    List<Product> findByPriceLessThanEqualAndAvailableTrue(BigDecimal price);

    // Ordenar p/ preço
    List<Product> findByAvailableTrueOrderByPriceAsc();

    List<Product> findByAvailableTrueOrderByPriceDesc();

//     // Query personalizada - Produtos mais vendidos
//     @Query("SELECT p FROM Product")
//     List<Product> findTopSellingProducts();

//     // Buscar p/ restaurante e categoria
//     @Query("SELECT * FROM Product WHERE restaurant.id = :restaurantId "
//             + "AND category = :category AND available = true")
//     List<Product> findByRestaurantAndCategory(@Param("restaurantId") Long restaurantId,
//             @Param("category") String category);

//     // Contar produtos p/ restaurante
//     @Query("SELECT COUNT (name) FROM Product WHERE restaurant.id = :restaurantId AND available = true")
//     Long countByRestaurantId(@Param("restaurantId") Long restaurantId);
}
