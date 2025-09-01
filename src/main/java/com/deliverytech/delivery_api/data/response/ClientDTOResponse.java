package com.deliverytech.delivery_api.data.response;

import jakarta.validation.constraints.*;

public class ClientDTOResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private boolean active;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;}

    // Getters e Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
