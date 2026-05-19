package com.company.mini_grocery.service;

import com.company.mini_grocery.dto.request.LoginRequest;
import com.company.mini_grocery.dto.request.RegisterRequest;
import com.company.mini_grocery.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
