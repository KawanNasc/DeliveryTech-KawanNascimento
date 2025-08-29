package com.deliverytech.delivery_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private boolean active;
    private LocalDateTime dateRegister;

    @OneToMany(mappedBy = "client")
    // Temporário - Enquanto s/ DTO'S
    @JsonIgnore
    private List<Order> requests;
}
