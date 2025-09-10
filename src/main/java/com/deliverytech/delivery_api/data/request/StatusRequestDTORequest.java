package com.deliverytech.delivery_api.data.request;

import com.deliverytech.delivery_api.enums.StatusRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class StatusRequestDTORequest {
    @NotNull(message = "Status é obrigatório")
    @JsonProperty("status")
    private StatusRequest statusRequest;

    @JsonProperty("note")
    private String note;

    // Constructors
    public StatusRequestDTORequest() {}

    public StatusRequestDTORequest(StatusRequest status) { this.statusRequest = status; }

    public StatusRequestDTORequest(StatusRequest status, String note) { this.statusRequest = status; this.note = note; }

    // Getters and Setters
    public StatusRequest getStatusRequest() { return statusRequest; }
    public void setStatus(StatusRequest statusRequest) { this.statusRequest = statusRequest; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    @Override
    public String toString() {
        return "StatusRequestDTORequest{" +
                "status=" + statusRequest +
                ", note='" + note + '\'' +
                '}';
    }
}