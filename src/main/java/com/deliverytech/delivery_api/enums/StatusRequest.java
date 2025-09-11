package com.deliverytech.delivery_api.enums;

public enum StatusRequest {
    PENDENTE("Pendente"),
    CONFIRMADO("Confirmado"),
    PREPARANDO("Preparando"),
    SAIU_PARA_ENTREGA("Saiu p/ entrega"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String description;

    StatusRequest(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}