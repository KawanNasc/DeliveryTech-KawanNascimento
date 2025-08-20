package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // Buscar p/ nome
    Optional<Restaurant> findByName(String name);

    // Buscar restaurantes ativos
    List<Restaurant> findByActiveTrue();

    // Buscar p/ categoria
    List<Restaurant> findByCategoryAndActiveTrue(String category);

    // Buscar p/ nome contendo (Case insensitive)
    List<Restaurant> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    // Byscar p/ av. mínima
    List<Restaurant> findByEvaluationGreaterThanEqualAndActiveTrue(BigDecimal evaluation);

    // Ordernar p/ av. (Descendente)
    List<Restaurant> findByActiveTrueOrderByEvaluationDesc();

    // Query personalizada - restaurantes com produtos
    @Query(value = "SELECT DISTINCT * FROM restaurant r JOIN products p ON r.id = p.restaurant_id WHERE r.active = true", nativeQuery = true)
    List<Restaurant> findRestaurantsWithProducts();

    // Buscar p/ faixa de taxa de entrega
    @Query(value = "SELECT * FROM restaurant WHERE deliveryFee BETWEEN :min AND :max AND active = true", nativeQuery = true)
    List<Restaurant> findByDeliveryFeeBetween(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // Categorias disponíveis
    @Query(value = "SELECT DISTINCT category FROM restaurant WHERE active = true ORDER BY category", nativeQuery = true)
    List<String> findAvailableCategories();
}
