package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Request;
import com.deliverytech.delivery_api.model.Client;

import com.deliverytech.delivery_api.enums.StatusRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    
    // Buscar pedidos p/cliente
    List<Request> findByClientorderByDateRequestDesc(Client client);

    // Buscar pedidos p/ id de cliente
    List<Request> findByClientIdOrderByDateRequestDesc(Long clientId);

    // Buscar p/ status
    List<Request> findByStatusOrderByDateRequestDesc(StatusRequest statusrequest);

    // Buscar p/ n° do pedido
    List<Request> findByDateRequestBetweenOrderByDateRequestDesc(LocalDateTime start, LocalDateTime finish);

    // Buscar pedidos do dia
    @Query("SELECT p FROM Request p WHERE DATE(p.dateRequest) = CURRENT_DATE ORDER BY p.dateRequest DESC")
    List<Request> findDailyRequests();

    // Buscar pedidos p/ restaurante
    @Query("SELECT p FROM Request p WHERE p.restaurant.id = :restaurantId ORDER BY p.dateRequest DESC")
    List<Request> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    // Relatório - Pedidos p/ status
    @Query("SELECT p.statusRequest, COUNT(p) FROM Request p GROUP BY p.status")
    List<Object[]> countRequestsByStatusRequest();

    // Pedidos pendentes (P/ dashboard)
    @Query("SELECT p FROM Request p WHERE p.statusRequest IN ('PENDENTE', 'CONFIRMADO', 'PREPARANDO') " + "ORDER BY p.dateRequest ASC")
    List<Request> findOngoingRequests();

    // Valor total de vendas p/ período
    @Query("SELECT SUM(p.totalValue) FROM Request p WHERE p.dataRequest BETWEEN :start AND :finish " + "AND p.statusRequest NOT IN ('CANCELADO')")
    BigDecimal calculateSellsPerTime(@Param("start") LocalDateTime start, @Param("finish") LocalDateTime finish);
}
