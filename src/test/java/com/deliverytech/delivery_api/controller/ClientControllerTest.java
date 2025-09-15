package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.config.TestDataConfiguration;
import com.deliverytech.delivery_api.data.request.ClientDTORequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestDataConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Testes de Integração do ClientController")
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar cliente com dados válidos")
    void should_CreateClient_When_ValidData() throws Exception {
        // Given
        ClientDTORequest clientDTORequest = new ClientDTORequest();
        clientDTORequest.setName("Júlia Aparecida");
        clientDTORequest.setEmail("julia@email.com");
        clientDTORequest.setPhone("11555555555");
        clientDTORequest.setCpf("98765432100");
        clientDTORequest.setAddress("Rua Teste");

        // When & Then
        mockMvc.perform(post("/api/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTORequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Júlia Aparecida")))
                .andExpect(jsonPath("$.email", is("julia@email.com")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando dados inválidos")
    void should_ReturnBadRequest_When_InvalidData() throws Exception {
        // Given
        ClientDTORequest clientDTORequest = new ClientDTORequest();
        clientDTORequest.setName("");
        clientDTORequest.setEmail("email-invalido");
        clientDTORequest.setPhone("123");
        clientDTORequest.setCpf("");
        clientDTORequest.setAddress("");

        // When & Then
        mockMvc.perform(post("/api/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTORequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.errors[*].field", hasItems("name", "email", "phone", "cpf", "address")));
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando email já existe")
    void should_ReturnBadRequest_When_EmailExists() throws Exception {
        // Given
        ClientDTORequest clientDTORequest = new ClientDTORequest();
        clientDTORequest.setName("João Braga");
        clientDTORequest.setEmail("joao@email.com");
        clientDTORequest.setPhone("11666666666");
        clientDTORequest.setCpf("98765432100");
        clientDTORequest.setAddress("Rua Teste");

        // When & Then
        mockMvc.perform(post("/api/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTORequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Email já cadastrado: joao@email.com")));
    }

    @Test
    @DisplayName("Deve retornar erro 409 quando CPF já existe")
    void should_ReturnConflict_When_CpfExists() throws Exception {
        // Given
        ClientDTORequest clientDTORequest = new ClientDTORequest();
        clientDTORequest.setName("Isaque Hugo");
        clientDTORequest.setEmail("isague@email.com");
        clientDTORequest.setPhone("12345678900");
        clientDTORequest.setCpf("12345678900");
        clientDTORequest.setAddress("Rua Teste");

        // When & Then
        mockMvc.perform(post("/api/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTORequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("CPF já cadastrado")))
                .andExpect(jsonPath("$.errorCode", is("CPF_ALREADY_EXISTS")))
                .andExpect(jsonPath("$.details.cpf", is("12345678900")));
    }
}