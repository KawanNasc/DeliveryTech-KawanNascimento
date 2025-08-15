package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.time.LocalDateTime;

// Controller responsável pelos endpoints de monitoramento de aplicação
// Demonstra o uso dos recursos modernos do Java 21
@RestController
@Tag(name = "Health check", description = "Check the API status and shows the system's information")
public class HealthController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Endpoint para verificar o statyus da aplicação
    // @return Map com informações de saúde da aplicação
    @Operation(summary = "Check the API status", description = "Returns basic infos as the status, timestamp, Java version and service.", responses = {
            @ApiResponse(responseCode = "200", description = "Working API", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        // Usando Map.of() (Java 9+) para criar mapa imutável
        Map<String, String> healthinfo = Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now().format(FORMATTER),
                "service", "DeliveryAPI",
                "javaVersion", System.getProperty("java.version"),
                "springBootVersion",
                getClass().getPackage().getImplementationVersion() != null
                        ? getClass().getPackage().getImplementationVersion()
                        : "3.2.x",
                "environment", "development");

        return ResponseEntity.ok(healthinfo);
    }

    // Endpoint com informações detalhadas da aplicação
    // Demonstra o uso de Records (Java 14+)
    // @return Appinfor com dados da aplicação
    @Operation(summary = "Application info's", description = "Returns application's info as the name, version, developer and frameworks", responses = {
            @ApiResponse(responseCode = "200", description = "Application's infos with success return", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppInfo.class)))
    })
    @GetMapping("/info")
    public ResponseEntity<AppInfo> info() {
        AppInfo appInfo = new AppInfo(
                "Delivery Tech API",
                "1.0.0.",
                "Kawan Nascimmento da Silva",
                System.getProperty("java.version"),
                "Spring Boot 3.2.x",
                LocalDateTime.now().format(FORMATTER),
                "Sistema de delivery moderno desenvolvido com as mais recentes tecnologias Java");

        return ResponseEntity.ok(appInfo);
    }

    // Record para demonstrar recurso do Java 14+ (disponível no JDK 21)
    // Records são classes imutáveis para DTOs (Data Transfer Objects)
    public record AppInfo(
            String application,
            String version,
            String developer,
            String javaVersion,
            String framework,
            String timestamp,
            String description) {
        // Construtor compacto p/ validação (Opcional)
        public AppInfo {
            if (application == null || application.isBlank()) {
                throw new IllegalArgumentException("Application name cannot be null or blank");
            }
        }
    }
}