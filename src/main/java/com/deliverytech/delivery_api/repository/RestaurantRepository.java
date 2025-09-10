package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Restaurant;

import com.deliverytech.delivery_api.data.SellsDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // Buscar p/ nome
    Optional<Restaurant> findByName(String name);

    List<Restaurant> findByCategory(String category);

    // Buscar restaurantes ativos
    Page<Restaurant> findByActiveTrue(Pageable pageable);

    // Buscar p/ categoria
    Page<Restaurant> findByCategoryAndActiveTrue(String category, Pageable pageable);

    // Buscar p/ nome contendo (Case insensitive)
    List<Restaurant> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    // Byscar p/ av. mínima
    List<Restaurant> findByEvaluationGreaterThanEqualAndActiveTrue(BigDecimal evaluation);

    // Ordernar p/ av. (Descendente)
    List<Restaurant> findByActiveTrueOrderByEvaluationDesc();

    // Taxa de entrega - ou =
    List<Restaurant> findByDeliveryFeeLessThanEqual(BigDecimal fee);

    // Top 5 restaurantes p/ nome (Ordem alfabética)
    List<Restaurant> findTop5ByOrderByNameAsc();

    boolean existsByNameAndAddress(String name, String address);

    boolean existsByNameAndAddressAndIdNot(String name, String address, Long id);

    // Query personalizada - restaurantes com produtos
    @Query(value = "SELECT DISTINCT * FROM restaurant r " +
        "JOIN products p ON r.id = p.restaurant_id " +
        "WHERE r.active = true", nativeQuery = true)
    List<Restaurant> findRestaurantsWithProducts();

    // Buscar p/ faixa de taxa de entrega
    @Query(value = "SELECT * FROM restaurant " + 
        "WHERE deliveryFee BETWEEN :min AND :max AND active = true", nativeQuery = true)
    List<Restaurant> findByDeliveryFeeBetween(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // Categorias disponíveis
    @Query(value = "SELECT DISTINCT category FROM restaurant " + 
        "WHERE active = true " + 
        "ORDER BY category", nativeQuery = true)
    List<String> findAvailableCategories();

    @Query("SELECT r.name as nameRestaurant, SUM(p.totalValue) as totalSells, COUNT(p.id) as quantityRequests FROM Restaurant r " +
        "LEFT JOIN Request p ON r.id = p.restaurant.id " +
        "GROUP BY r.id, r.name")
    List<SellsDTO> calcuateSellsPerRestaurant();
}
