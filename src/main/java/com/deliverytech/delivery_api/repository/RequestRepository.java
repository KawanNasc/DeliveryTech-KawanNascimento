package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Request;
import com.deliverytech.delivery_api.model.Client;

import com.deliverytech.delivery_api.enums.StatusRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    // Buscar pedidos p/cliente
    List<Request> findByClientOrderByDateRequestDesc(Client client);

    // Buscar pedidos p/ id de cliente
    List<Request> findByClientIdOrderByDateRequestDesc(Long clientId);

    // Buscar p/ status
    List<Request> findByStatusRequestOrderByDateRequestDesc(StatusRequest statusRequest);

    // Buscar p/ n° do pedido
    Page<Request> findByDateRequestBetweenOrderByDateRequestDesc(LocalDateTime start, LocalDateTime end, Pageable pageable);

    // 10 pedidos mais recentes
    List<Request> findTop10ByOrderByDateRequestDesc();

    // New method for counting
    long countByDateRequestBetween(LocalDateTime start, LocalDateTime end);

    // Buscar pedidos do dia
    @Query(value = "SELECT * FROM request " +
            "WHERE DATE(dateRequest) = CURRENT_DATE " +
            "ORDER BY dateRequest DESC", nativeQuery = true)
    List<Request> findDailyRequests();

    // Buscar pedidos p/ restaurante
    @Query(value = "SELECT * FROM request req " + 
        "JOIN restaurant res ON res.id = req.restaurant_id " +
        "WHERE req.restaurant_id = :restaurant_id " +
        "ORDER BY req.dateRequest DESC", nativeQuery = true)
    List<Request> findByRestaurantId(@Param("restaurant_id") Long restaurant_id);

    // Relatório - Pedidos p/ status
    @Query(value = "SELECT statusRequest, COUNT(*) FROM request " +
        "GROUP BY statusRequest", nativeQuery = true)
    List<Object[]> countRequestsByStatusRequest();

    // Pedidos pendentes (P/ dashboard)
    @Query(value = "SELECT * FROM request " +
        "WHERE statusRequest IN ('PENDENTE', 'CONFIRMADO', 'PREPARANDO') " +
        "ORDER BY dateRequest ASC", nativeQuery = true)
    List<Request> findOngoingRequests();

    // Valor total de vendas p/ período
    @Query(value = "SELECT SUM(totalValue) FROM request " +
        "WHERE dateRequest BETWEEN :start AND :end AND statusRequest NOT IN ('CANCELADO')", nativeQuery = true)
    BigDecimal calculateSellsPerTime(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT p.restaurant.name, SUM(p.totalValue) FROM Request p " +
    "GROUP BY p.restaurant.id, p.restaurant.name ORDER BY SUM(p.totalValue) DESC")
    List<Object> calculateTotalSallesByRestaurant();

    @Query("SELECT p FROM Request p " +
        "WHERE p.totalValue > :value " +
        "ORDER BY p.totalValue DESC")
    List<Request> findRequestsWithTotalValueGreaterThan(@Param("value") BigDecimal value);

    @Query("SELECT p FROM Request p " +
        "WHERE p.dateRequest BETWEEN :start AND :end AND p.statusRequest = :statusRequest " +
        "ORDER BY p.dateRequest DESC")
    List<Request> findRequestsPerDataRangeAndStatusRequest(@Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end, @Param("statusRequest") StatusRequest statusRequest);
}
