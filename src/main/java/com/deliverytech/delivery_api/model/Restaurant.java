package com.deliverytech.delivery_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private String address;
    private String phone;
    private BigDecimal deliveryFee;
    private int evaluation;
    private boolean active;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<Product> products;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<Order> orders;
}
