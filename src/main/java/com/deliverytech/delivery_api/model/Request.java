package com.deliverytech.delivery_api.model;

import com.deliverytech.delivery_api.enums.StatusRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateRequest;
    private String note;
    private String paymentWay;
    private String deliveryAddress;
    private String cep;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private Integer deliveryTime;
    private String workHours;
    private BigDecimal totalValue;

    @Enumerated(EnumType.STRING)
    private StatusRequest statusRequest;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ItemRequest> itemsRequest;

    public void addItem(ItemRequest item) {
        throw new UnsupportedOperationException("Erro ao adicionar item");
    }

    public void confirm() {
        throw new UnsupportedOperationException("Erro ao confirmar pedido");
    }
}
