package com.deliverytech.delivery_api.config;

import com.deliverytech.delivery_api.data.request.LoginDTORequest;
import com.deliverytech.delivery_api.data.request.RegisterDTORequest;
import com.deliverytech.delivery_api.data.response.LoginDTOResponse;
import com.deliverytech.delivery_api.data.response.UserDTOResponse;
import com.deliverytech.delivery_api.model.User;

import com.deliverytech.delivery_api.security.JwtUtil;
import com.deliverytech.delivery_api.security.SecurityUtils;

import com.deliverytech.delivery_api.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Operações de autenticação e autorização")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @PostMapping("/login")
    @Operation(summary = "Fazer login", description = "Autentica um usuário e retorna um token JWT", tags = {"Autenticação" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginDTOResponse.class), examples = @ExampleObject(name = "Login bem-sucedido", value = """
                    {
                        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                        "type": "Bearer",
                        "expiracao": 86400000,
                        "usuario": {
                            "id": 1,
                            "nome": "João Silva",
                            "email": "joao@email.com",
                            "role": "CLIENT"
                        }
                    }
                    """))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<?> login(
            @Parameter(description = "Credenciais de login") @Valid @RequestBody LoginDTORequest loginDTORequest) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTORequest.getEmail(),
                            loginDTORequest.getPassword()));

            // Load user details
            UserDetails userDetails = authService.loadUserByUsername(loginDTORequest.getEmail());

            // Generate JWT token
            String token = jwtUtil.generateToken(userDetails);

            // Create response
            User user = (User) userDetails;
            UserDTOResponse userResponse = new UserDTOResponse(user);
            LoginDTOResponse loginResponse = new LoginDTOResponse(token, jwtExpiration, userResponse);

            return ResponseEntity.ok(loginResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria uma nova conta de usuário no sistema", tags = {
            "Autenticação" })
    public ResponseEntity<?> register(
            @Parameter(description = "Dados para criação da conta") @Valid @RequestBody RegisterDTORequest registerRequest) {
        try {
            // Check if email exists
            if (authService.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Email already in use");
            }

            // Create new user
            User newUser = authService.createUser(registerRequest);

            // Return user data (no token - user must login)
            UserDTOResponse userResponse = new UserDTOResponse(newUser);
            return ResponseEntity.status(201).body(userResponse);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating user: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            User loggedUser = SecurityUtils.getCurrentUser();
            UserDTOResponse userResponse = new UserDTOResponse(loggedUser);
            return ResponseEntity.ok(userResponse);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }
}