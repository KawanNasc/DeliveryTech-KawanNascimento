package com.deliverytech.delivery_api.data.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "Data for restaurant register")
public class RestaurantDTORequest {
    @Schema(description = "Restaurant's name", example = "Pizza Express")
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2-100 caracteres")
    private String name;

    @Schema(description = "Restauran's category", example = "Italiana", allowableValues = {"Italiana", "Brasileira", "Japonesa", "Mexicana", "Árabe"})
    @NotBlank(message = "Categoria é obrigatória")
    private String category;

    @Schema(description = "Restaurant's complete address", example = "Rua das Flores, 123 - Centro")
    @NotBlank(message = "Endereço deve ter no máximo 200 caracteres")
    private String address;

    @Schema(description = "Phone for contact", example = "11999999999")
    @NotBlank(message = "Telefone é obrigatórip")
    @Pattern(regexp = "\\d{10, 11}", message = "Telefone deve ter 10-11 dígitos")
    private String phone;

    @Schema(description = "Delivery fee in R$", example = "5.50", minimum = "0")
    @NotNull(message = "Taxa de entrega é obrigatória")
    @DecimalMin(value = "0.0", message = "Taxa de entrega deve ser positiva")
    private BigDecimal deliveryFee;

    @Schema(description = "Estimated time for delivery in minutes", example = "45", minimum = "10", maximum = "120")
    @NotNull(message = "Tempo de entrega é obrigatório")
    @Min(value = 10, message = "Tempo mínimo é 10 minutos")
    @Max(value = 120, message = "Tempo máximo é 120 minutos")
    private Integer deliveryTime;

    @Schema(description = "Work hours", example = "08:00-22:00")
    @NotBlank(message = "Horário de funcionamento é obrigatório")
    private String workHours;

    // Getters e Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public BigDecimal getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(BigDecimal deliveryFee) { this.deliveryFee = deliveryFee; }

    public Integer getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(Integer deliveryTime) { this.deliveryTime = deliveryTime; }

    public String getWorkHours() { return workHours; }
    public void setWorkHours(String workHours) { this.workHours = workHours; }
}
