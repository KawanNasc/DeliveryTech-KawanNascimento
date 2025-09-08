package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.data.request.RestaurantDTORequest;

import com.deliverytech.delivery_api.model.Restaurant;

import com.deliverytech.delivery_api.repository.RestaurantRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RestaurantControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private RestaurantDTORequest restaurantDTORequest;
    private Restaurant savedRestaurant;

    @BeforeEach
    void setUp() {
        restaurantRepository.deleteAll();

        restaurantDTORequest = new RestaurantDTORequest();
        restaurantDTORequest.setName("Pizza Express");
        restaurantDTORequest.setCategory("Italiana");
        restaurantDTORequest.setAddress("Rua das Flores, 123");
        restaurantDTORequest.setPhone("11999999999");
        restaurantDTORequest.setDeliveryFee(new BigDecimal("5.50"));
        restaurantDTORequest.setDeliveryTime(45);
        restaurantDTORequest.setWorkHours("08:00-22:00");

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Burguer King");
        restaurant.setCategory("Americana");
        restaurant.setAddress("Av. Paulista, 1000");
        restaurant.setPhone("1188888888");
        restaurant.setDeliveryFee(new BigDecimal("4.00"));
        restaurant.setDeliveryTime(40);
        restaurant.setWorkHours("10:00-23:00");
        restaurant.setActive(true);
        savedRestaurant = restaurantRepository.save(restaurant);
    }

    @Test
    void shouldRegisterRestaurantWithSuccess() throws Exception {
        mockMvc.perform(post("/restaurant")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(restaurantDTORequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.name").value("Pizza Express"))
            .andExpect(jsonPath("$.category").value("Italiana"))
            .andExpect(jsonPath("$.active").value(true))
            .andExpect(jsonPath("$.message").value("Restaurante criado com sucesso"));
    }

    @Test
    void shouldRejectRestaurantWithInvalidData() throws Exception {
        restaurantDTORequest.setName(""); // Nome invádlido
        restaurantDTORequest.setPhone("123"); // Telefone invádlido

        mockMvc.perform(post("/restaurant")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(restaurantDTORequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.error.code").value("VALIDATION_ERROR"))
            .andExpect(jsonPath("$.error.details.name").exists())
            .andExpect(jsonPath("$.error.details.phone").exists());
    }

    @Test
    void shouldFindRestaurantPerId() throws Exception {
        mockMvc.perform(post("/restaurant/{id}", savedRestaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.id").value(savedRestaurant.getId()))
            .andExpect(jsonPath("$.data.name").value("Burguer King"))
            .andExpect(jsonPath("$.active").value("Americana"));
    }

    @Test
    void shouldReturn404ForInexistentRestaurant() throws Exception {
        mockMvc.perform(post("/restaurant/{id}", 999L))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.error.code").value("ENTITY_NOT_FOUND"));
    }

    @Test
    void shouldListRestaurantsWithPagination() throws Exception {
        mockMvc.perform(post("/restaurant")
            .param("page", "0")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.page.number").value(0))
            .andExpect(jsonPath("$.page.size").value(10))
            .andExpect(jsonPath("$.page.totalElements").value(1));
    }

    @Test
    void shouldUpdateRestaurantWithSuccess() throws Exception {
        restaurantDTORequest.setName("Pizza Express atualizada");

        mockMvc.perform(post("/restaurant/{id}", savedRestaurant.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(restaurantDTORequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.name").value("Pizza Express atualizada"))
            .andExpect(jsonPath("$.message").value("Restaurante atualizado com sucesso"));
    }

    @Test
    void shouldUpdateStatusRestaurant() throws Exception {
        mockMvc.perform(post("/restaurant/{id}/status", savedRestaurant.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(restaurantDTORequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.active").value(false))
            .andExpect(jsonPath("$.message").value("Status alterado com sucesso"));
    }

    @Test
    void shouldFindRestaurantsPerCategory() throws Exception {
        mockMvc.perform(post("/restaurant/category/{category}", "Americana"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].category").value("Americana"));
    }
}
