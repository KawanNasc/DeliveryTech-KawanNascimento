package com.deliverytech.delivery_api.model;

import com.deliverytech.delivery_api.enums.StatusRequest;

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
    private String addressDelivery;
    private BigDecimal subtotal;
    private BigDecimal deliveryfee;
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
    private List<ItemRequest> itemsRequest;

    public void addItem(ItemRequest item) {
        throw new UnsupportedOperationException("Unimplemented method 'addItem'");
    }

    public void confirm() {
        throw new UnsupportedOperationException("Unimplemented method 'confirm'");
    }

    public String getNote() {
        throw new UnsupportedOperationException("Unimplemented method 'getNote'");
    }

    public void setNote(String string) {
        throw new UnsupportedOperationException("Unimplemented method 'setNote'");
    }
}
