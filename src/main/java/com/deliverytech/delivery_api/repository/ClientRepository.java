package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Buscar clientes p/ e-mail (Método derivado)
    Optional<Client> findByEmail(String email);

    // Verificar se o e-mail já existe
    boolean existsByEmail(String email);

    // Buscar clientes ativos
    List<Client> findByActiveTrue();

    // Buscar clientes p/ nome (Contendo)
    List<Client> findByNameContainingIgnoreCase(String name);

    // Buscar clientes p/ telefone
    Optional<Client> findByPhone(String phone);

    // Query personalizada - Clientes c/ pedidos
    @Query(value = "SELECT DISTINCT * FROM client c JOIN requests WHERE c.active = true", nativeQuery = true)
    List<Client> findClientsWithRequests();

    // Query nativa - Clientes p/ cidade
    @Query(value = "SELECT * FROM client WHERE address LIKE %:city% AND active = true", nativeQuery = true)
    List<Client> findByCity(@Param("city") String city);

    // Contar clientes ativos
    @Query(value = "SELECT COUNT(name) FROM client WHERE active = true", nativeQuery = true)
    Long countActiveClients();
}
