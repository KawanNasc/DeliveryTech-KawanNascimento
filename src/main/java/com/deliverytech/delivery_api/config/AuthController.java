package com.deliverytech.delivery_api.config;

import com.deliverytech.delivery_api.data.request.LoginDTORequest;
import com.deliverytech.delivery_api.data.request.RegisterDTORequest;
import com.deliverytech.delivery_api.data.response.LoginDTOResponse;
import com.deliverytech.delivery_api.data.response.UserDTOResponse;
import com.deliverytech.delivery_api.model.User;

import com.deliverytech.delivery_api.security.JwtUtil;
import com.deliverytech.delivery_api.security.SecurityUtils;

import com.deliverytech.delivery_api.services.AuthService;

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
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTORequest loginDTORequest) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTORequest.getEmail(),
                    loginDTORequest.getPassword()
                )
            );

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
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTORequest registerRequest) {
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