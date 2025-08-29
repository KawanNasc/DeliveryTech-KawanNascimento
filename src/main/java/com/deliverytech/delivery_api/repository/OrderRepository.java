package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Order;
import com.deliverytech.delivery_api.model.Client;

import com.deliverytech.delivery_api.enums.StatusOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Buscar pedidos p/cliente
    List<Order> findByClientOrderByDateOrderDesc(Client client);

    // Buscar pedidos p/ id de cliente
    List<Order> findByClientIdOrderByDateOrderDesc(Long clientId);

    // Buscar p/ status
    List<Order> findByStatusOrderOrderByDateOrderDesc(StatusOrder statusOrder);

    // Buscar p/ n° do pedido
    List<Order> findByDateOrderBetweenOrderByDateOrderDesc(LocalDateTime start, LocalDateTime end);

    // 10 pedidos mais recentes
    List<Order> findTop10ByOrderByDateOrderDesc();

    // Buscar pedidos do dia
    @Query(value = "SELECT * FROM order " +
            "WHERE DATE(dateOrder) = CURRENT_DATE " +
            "ORDER BY dateOrder DESC", nativeQuery = true)
    List<Order> findDailyOrder();

    // Buscar pedidos p/ restaurante
    @Query(value = "SELECT * FROM order ord " + 
        "JOIN restaurant res ON res.id = ord.restaurant_id " +
        "WHERE ord.restaurant_id = :restaurant_id " +
        "ORDER BY ord.dateOrder DESC", nativeQuery = true)
    List<Order> findByRestaurantId(@Param("restaurant_id") Long restaurant_id);

    // Relatório - Pedidos p/ status
    @Query(value = "SELECT statusOrder, COUNT(*) FROM order " +
        "GROUP BY statusOrder", nativeQuery = true)
    List<Object[]> countOrdersByStatusOrder();

    // Pedidos pendentes (P/ dashboard)
    @Query(value = "SELECT * FROM order " +
        "WHERE statusOrder IN ('PENDENTE', 'CONFIRMADO', 'PREPARANDO') " +
        "ORDER BY dateROrder ASC", nativeQuery = true)
    List<Order> findOngoingOrders();

    // Valor total de vendas p/ período
    @Query(value = "SELECT SUM(totalValue) FROM order " +
        "WHERE dateOrder BETWEEN :start AND :end AND statusOrder NOT IN ('CANCELADO')", nativeQuery = true)
    BigDecimal calculateSellsPerTime(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT o.restaurant.name, SUM(o.totalValue) FROM Order o " +
    "GROUP BY o.restaurant.id, o.restaurant.name ORDER BY SUM(o.totalValue) DESC")
    List<Object> calculateTotalSallesByRestaurant();

    @Query("SELECT o FROM Order o " +
        "WHERE o.totalValue > :value " +
        "ORDER BY o.totalValue DESC")
    List<Order> findOrdersWithTotalValueGreaterThan(@Param("value") BigDecimal value);

    @Query("SELECT o FROM Order o " +
        "WHERE o.dateOrder BETWEEN :start AND :end AND p.statusOrder = :statusOrder " +
        "ORDER BY p.dateOrder DESC")
    List<Order> findOrdersPerDataRangeAndStatusOrder(@Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end, @Param("statusOrder") StatusOrder statusOrder);
}
