package com.deliverytech.delivery_api.config;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Default wrapper for API responses")
public class ApiResponseWrapper<T> {
    @Schema(description = "Indicates if the operation completes well success", example = "true")
    private boolean success;

    @Schema(description = "Response data")
    private T data;

    @Schema(description = "Descritive message", example = "Operation completes with success")
    private String message;

    @Schema(description = "Response timestamp", example = "2025-09-06T20:55:40")
    private LocalDateTime timestamp;

    public ApiResponseWrapper() { this.timestamp = LocalDateTime.now(); }

    public ApiResponseWrapper(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponseWrapper<T> success(T data, String message) { return new ApiResponseWrapper<>(true, data, message); }

    public static <T> ApiResponseWrapper<T> error(String message) { return new ApiResponseWrapper<>(false, null, message); }

    // Getters e Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}