package com.quipux.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quipux.model.TokenResponse;
import com.quipux.model.Usuario;
import com.quipux.service.AutenticacionService;

@RestController
@RequestMapping("/api/auth")
public class AutenticacionController {
	private final AutenticacionService authService;

    public AutenticacionController(AutenticacionService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        TokenResponse token = authService.login(request.get("email"), request.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(authService.register(usuario));
    }
    
    
    
}
