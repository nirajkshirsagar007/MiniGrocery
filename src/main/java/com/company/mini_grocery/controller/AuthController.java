package com.company.mini_grocery.controller;

import com.company.mini_grocery.common.ApiResponse;
import com.company.mini_grocery.dto.request.LoginRequest;
import com.company.mini_grocery.dto.request.RegisterRequest;
import com.company.mini_grocery.dto.response.AuthResponse;
import com.company.mini_grocery.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.cert.TrustAnchor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request){
        AuthResponse response = authService.register(request);

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("User register successfully")
                .data(response)
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @Valid @RequestBody LoginRequest request){
        AuthResponse response = authService.login(request);

        return  ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Login successful")
                .data(response)
                .build();
    }

}
