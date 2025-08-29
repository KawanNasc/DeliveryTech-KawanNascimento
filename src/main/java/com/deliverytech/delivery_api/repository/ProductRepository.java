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

    // Apenas produtos disponíveis
    List<Product> findByAvailableTrue();

    // Buscar produtos p/ restaurante
    List<Product> findByRestaurantAndAvailableTrue(Restaurant restaurant);

    // Buscar produtos p/ ID do restaurante
    List<Product> findByRestaurantIdAndAvailableTrue(Long restaurant_id);

    // Buscar p/ categoria
    List<Product> findByCategoryAndAvailableTrue(String category);

    // Buscar p/ nome contendo
    List<Product> findByNameContainingIgnoreCaseAndAvailableTrue(String name);

    // Buscar p/ faixa de preço
    List<Product> findByPriceBetweenAndAvailableTrue(BigDecimal priceMin, BigDecimal priceMax);

    // Buscar p/ faixa de preço (- ou =)
    List<Product> findByPriceLessThanEqual(BigDecimal price);

    // Buscar produtos mais baratos que um valor
    List<Product> findByPriceLessThanEqualAndAvailableTrue(BigDecimal price);

    // Ordenar p/ preço
    List<Product> findByAvailableTrueOrderByPriceAsc();

    List<Product> findByAvailableTrueOrderByPriceDesc();

    // Query personalizada - Produtos mais vendidos
    @Query(value = "SELECT * FROM product p " +
            "JOIN item_order i ON p.id = i.product_id " +
            "GROUP BY p.name " +
            "ORDER BY COUNT(i.product_id)", nativeQuery = true)
    List<Product> findTopSellingProducts();

    // Buscar p/ restaurante e categoria
    @Query(value = "SELECT * FROM product p " +
            "JOIN restaurant r ON r.id = :restaurant_id " +
            "WHERE p.category = :category AND p.available = true", nativeQuery = true)
    List<Product> findByRestaurantAndCategory(@Param("restaurant_id") Long restaurant_id,
            @Param("category") String category);

    // Contar produtos p/ restaurante
    @Query(value = "SELECT COUNT (name) FROM product p " +
            "JOIN restaurant r ON r.id = p.restaurant_id " +
            "WHERE r.id = :restaurant_id AND available = true", nativeQuery = true)
    Long countByRestaurantId(@Param("restaurant_id") Long restaurant_id);

    @Query(value = "SELECT p.name, COUNT(io.product_id) AS numberSells FROM product p " + 
            "LEFT JOIN item_order io ON p.id = io.product_id " + 
            "GROUP BY p.id, p.name " +
            "ORDER BY numberSells DESC LIMIT 5", nativeQuery = true)
    List<Object[]> rankingMostSelledProducts();
}
