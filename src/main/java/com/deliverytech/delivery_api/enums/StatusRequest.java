package com.deliverytech.delivery_api.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status possíveis de um pedido no sistema") 
public enum StatusRequest {

    @Schema(description = "Pedido foi criado mas ainda não foi confirmado pelo restaurante")
    PENDENTE("Pendente"),

    @Schema(description = "Pedido foi confirmado pelo restaurante e está sendo preparado") 
    CONFIRMADO("Confirmado"),

    @Schema(description = "Pedido está sendo preparado na cozinha") 
    PREPARANDO("Preparando"),

    @Schema(description = "Pedido está pronto e aguardando entregador") 
    SAIU_PARA_ENTREGA("Saiu p/ entrega"),

    @Schema(description = "Pedido foi entregue com sucesso ao cliente")
    ENTREGUE("Entregue"),

      @Schema(description = "Pedido foi cancelado pelo cliente ou restaurante")
    CANCELADO("Cancelado");

    private final String description;

    StatusRequest(String description) { this.description = description; }

    public String getDescription() { return description; }
}