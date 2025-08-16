package com.deliverytech.delivery_api.model;

import jakarta.persistence.*;
import lombok.Data;
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

    @OneToMany(mappedBy = "client")
    private List<Request> requests;
}
