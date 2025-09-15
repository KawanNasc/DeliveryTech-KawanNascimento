package com.deliverytech.delivery_api.config;

import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.model.Product;
import com.deliverytech.delivery_api.repository.ClientRepository;
import com.deliverytech.delivery_api.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;

import java.math.BigDecimal;

@TestConfiguration
public class TestDataConfiguration {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void setupTestData() {
        // Limpar dados existentes
        clientRepository.deleteAll();
        productRepository.deleteAll();

        // Criar dados de teste
        Client cliente = new Client();
        cliente.setName("João Teste");
        cliente.setEmail("joao.teste@email.com");
        cliente.setPhone("11999999999");
        cliente.setCpf("12345678900");
        cliente.setAddress("Rua Teste");
        cliente.setActive(true);
        clientRepository.save(cliente);

        Product product = new Product();
        product.setName("Pizza Teste");
        product.setDescription("Pizza para testes");
        product.setPrice(BigDecimal.valueOf(29.90));
        product.setAvailable(true);
        productRepository.save(product);
    }
}