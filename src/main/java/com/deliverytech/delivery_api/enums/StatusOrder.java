package com.deliverytech.delivery_api.enums;

public enum StatusOrder {
    PENDENTE("Pendente"),
    CONFIRMADO("Confirmado"),
    PREPARANDO("Preparando"),
    SAIU_PARA_ENTREGA("Saiu p/ entrega"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String description;

    StatusOrder(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
