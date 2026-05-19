package com.company.mini_grocery.service.impl;

import com.company.mini_grocery.dto.request.LoginRequest;
import com.company.mini_grocery.dto.request.RegisterRequest;
import com.company.mini_grocery.dto.response.AuthResponse;
import com.company.mini_grocery.entity.Role;
import com.company.mini_grocery.entity.User;
import com.company.mini_grocery.exception.InvalidCredentialexception;
import com.company.mini_grocery.repository.UserRepository;
import com.company.mini_grocery.security.JwtService;
import com.company.mini_grocery.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request){
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STAFF)
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request)
    {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new InvalidCredentialexception("Invalid email"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())){
            throw new InvalidCredentialexception("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return  AuthResponse.builder()
                .token(token)
                .build();

    }
}
