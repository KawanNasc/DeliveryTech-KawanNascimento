package com.deliverytech.delivery_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal unitaryPrice;
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request requests;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product products;
}
