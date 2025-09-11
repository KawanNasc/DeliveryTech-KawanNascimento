package com.deliverytech.delivery_api.config;

import com.deliverytech.delivery_api.data.request.StatusRequestDTORequest;
import com.deliverytech.delivery_api.enums.StatusRequest;
import com.deliverytech.delivery_api.model.*;
import com.deliverytech.delivery_api.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        // Utilizando apenas inserção de dados no data.sql = Descomente
        // Código comentado para não executar conflito de relacionamento em métodos
        // insert não existentes
        requestRepository.deleteAll();
        productRepository.deleteAll();
        restaurantRepository.deleteAll();
        clientRepository.deleteAll();

        insertClients();
        insertRestaurants();
        insertProducts();
        insertRequests();

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
        client3.setActive(true);

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
        restaurant1.setDeliveryTime(7);
        restaurant1.setWorkHours("6:00-22:00");
        restaurant1.setZip("12345-6789");
        restaurant1.setEvaluation(2);
        restaurant1.setActive(true);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Burger King");
        restaurant2.setCategory("Fast Food");
        restaurant2.setAddress("R. Central, 200");
        restaurant2.setPhone("1144444444");
        restaurant2.setDeliveryFee(new BigDecimal("5"));
        restaurant2.setDeliveryTime(5);
        restaurant2.setWorkHours("7:30-22:50");
        restaurant2.setZip("12345-6789");
        restaurant2.setEvaluation(5);
        restaurant2.setActive(true);

        restaurantRepository.saveAll(Arrays.asList(restaurant1, restaurant2));
        System.out.println("2 restaurantes inseridos");
    }

    private void insertProducts() {
        System.out.println("Inserindo produto");

        Product product1 = new Product();
        product1.setName("Guaravita");
        product1.setDescription("Gourmet");
        product1.setPrice(new BigDecimal(12.50));
        product1.setCategory("Panqueca");
        product1.setAvailable(true);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        product1.setRestaurant(restaurant);

        productRepository.saveAll(Arrays.asList(product1));
        System.out.println("1 produto inserido");
    }

    private void insertRequests() {
        System.out.println("Inserindo pedidos");

        // Create a new Request and set its properties
        Request request = new Request();
        request.setDateRequest(LocalDateTime.now());
        request.setNote("Pedio a ser entregue");
        request.setPaymentWay("PIX");
        request.setDeliveryAddress("Av. Principal, 100");
        request.setCep("00000-000");
        request.setSubtotal(new BigDecimal(10));
        request.setDeliveryFee(new BigDecimal(10));
        request.setDeliveryTime(25);
        request.setWorkHours("06-23:00");
        request.setTotalValue(new BigDecimal(20));

        // Create the DTO for the status
        StatusRequestDTORequest statusRequestDTO = new StatusRequestDTORequest();
        statusRequestDTO.setStatus(StatusRequest.CONFIRMADO);
        request.setStatusRequest(statusRequestDTO.getStatusRequest());

        Client client = new Client();
        client.setId(1L);
        request.setClient(client);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        request.setRestaurant(restaurant);

        List<ItemRequest> items = new ArrayList<>();

        ItemRequest item1 = new ItemRequest();
        Product product1 = new Product();
        product1.setId(1L); // ID do produto Guaravita
        item1.setQuantity(2);
        item1.setUnitaryPrice(new BigDecimal(10));
        item1.setSubtotal(new BigDecimal(10));
        item1.setProducts(product1);
        item1.setRequest(request);
        items.add(item1);

        ItemRequest item2 = new ItemRequest();
        Product product2 = new Product();
        product2.setId(1L); // ID do produto Guaravita
        item2.setQuantity(1);
        item2.setUnitaryPrice(new BigDecimal(10));
        item2.setSubtotal(new BigDecimal(10));
        item2.setProducts(product2);
        item2.setRequest(request);
        items.add(item2);

        request.setItemsRequest(items);

        requestRepository.save(request);
        System.out.println("1 pedido inserido");
    }

    private void testSelects() {
        System.out.println("\n Testando consultas dos repositories");

        System.out.println("\n Testes ClientRepository");

        var clientPerEmail = clientRepository.findByEmail("joao@email.com");
        System.out.println("Cliente p/ e-mail: "
                + (clientPerEmail.isPresent() ? clientPerEmail.get().getName() : "Não encontado"));

        var activeClients = clientRepository.findByActiveTrue();
        System.out.println("Clientes ativos: " + activeClients.size());

        var clientsPerName = clientRepository.findByNameContainingIgnoreCase("silva");
        System.out.println("Clientes com 'silva' no nome: " + clientsPerName.size());

        boolean emailExists = clientRepository.existsByEmail("maria@email.com");
        System.out.println("Email maria@email.com existe: " + emailExists);

        // Mais testes
    }
}