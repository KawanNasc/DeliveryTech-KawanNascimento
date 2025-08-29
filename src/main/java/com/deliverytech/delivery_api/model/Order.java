package com.deliverytech.delivery_api.model;

import com.deliverytech.delivery_api.enums.StatusOrder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateOrder;
    private String note;
    private BigDecimal subtotal;
    private BigDecimal totalValue;

    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ItemOrder> itemsOrder;

    public void addItem(ItemOrder item) {
        throw new UnsupportedOperationException("Erro ao adicionar item");
    }

    public void confirm() {
        throw new UnsupportedOperationException("Erro ao confirmar pedido");
    }
}
