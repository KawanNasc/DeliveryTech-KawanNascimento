package com.deliverytech.delivery_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@Schema(description = "En dade que representa um restaurante no sistema")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Iden ficador único do restaurante", example = "1")
    private Long id;

    @Column(nullable = false) 
    @NotBlank(message = "Nome é obrigatório") 
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Schema(description = "Nome do restaurante", example = "Pizza Palace", required = true) 
    private String name;

    @Column(nullable = false) 
    @NotBlank(message = "Categoria é obrigatória") 
    @Schema(description = "Categoria/ po de culinária do restaurante", example = "Italiana", required = true)
    private String category;

    private String address;

    @Column(nullable = false) 
    @NotBlank(message = "Telefone é obrigatório") 
    @Schema(description = "Telefone de contato do restaurante", example = "(11) 1234-5678", required = true)
    private String phone;

    private BigDecimal deliveryFee;
    private Integer deliveryTime;
    private String workHours;
    private String zip;

    @Column(precision = 3, scale = 2) 
    @Schema(description = "Avaliação média do restaurante (0 a 5 estrelas)", 
            example = "4.5", 
            minimum = "0", 
            maximum = "5") 
    private Integer evaluation;

    @Column(nullable = false) 
    @Schema(description = "Indica se o restaurante está a vo no sistema", 
            example = "true", 
            defaultValue = "true") 
    private boolean active;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
    @Schema(description = "Lista de produtos oferecidos pelo restaurante")
    @JsonIgnore
    private List<Product> products;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<Request> requests;
}