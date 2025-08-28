package com.deliverytech.delivery_api.config;

import com.deliverytech.delivery_api.model.*;
import com.deliverytech.delivery_api.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Iniciando carga de dados de teste");

        requestRepository.deleteAll();
        productRepository.deleteAll();
        restaurantRepository.deleteAll();
        clientRepository.deleteAll();

        insertClients();
        insertRestaurants();

        testSelects();

        System.out.println("Carga de dados concluída");
    }

    private void insertClients() {
        System.out.println("Inserindo clientes");

        Client client1 = new Client();
        client1.setName("João Silva");
        client1.setEmail("joao@email.com");
        client1.setPhone("11999999999");
        client1.setAddress("Rua A, 123");
        client1.setActive(true);

        Client client2 = new Client();
        client2.setName("Maria Santos");
        client2.setEmail("maria@email.com");
        client2.setPhone("1188888888");
        client2.setAddress("Rua B, 456");
        client2.setActive(true);

        Client client3 = new Client();
        client3.setName("Pedro Oliveira");
        client3.setEmail("pedro@email.com");
        client3.setPhone("11777777777");
        client3.setAddress("Rua C, 789");
        client3.setActive(false);

        clientRepository.saveAll(Arrays.asList(client1, client2, client3));
        System.out.println("3 clientes inseridos");
    }

    private void insertRestaurants() {
        System.out.println("inserindo restaurantes");

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("Pizza Express");
        restaurant1.setCategory("Italiana");
        restaurant1.setAddress("Av. Principal, 100");
        restaurant1.setPhone("1133333333");
        restaurant1.setDeliveryFee(new BigDecimal("3.5"));
        restaurant1.setActive(true);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Burger King");
        restaurant2.setCategory("Fast Food");
        restaurant2.setAddress("R. Central, 200");
        restaurant2.setPhone("1144444444");
        restaurant2.setDeliveryFee(new BigDecimal("5"));
        restaurant2.setActive(true);

        restaurantRepository.saveAll(Arrays.asList(restaurant1, restaurant2));
        System.out.println("2 restaurantes inseridos");
    }

    private void testSelects() {
        System.out.println("\n Testando consultas dos repositories");

        System.out.println("\n Testes ClientRepository");

        var clientPerEmail = clientRepository.findByEmail("joao@email.com");
        System.out.println("Cliente p/ e-mail: " + (clientPerEmail.isPresent() ? clientPerEmail.get().getName() : "Não encontado"));

        var activeClients = clientRepository.findByActiveTrue();
        System.out.println("Clientes ativos: " + activeClients.size());

        var clientsPerName = clientRepository.findByNameContainingIgnoreCase("silva");
        System.out.println("Clientes com 'silva' no nome: " + clientsPerName.size());

        boolean emailExists = clientRepository.existsByEmail("maria@email.com");
        System.out.println("Email maria@email.com existe: " + emailExists);

        // Mais testes
    }
}
