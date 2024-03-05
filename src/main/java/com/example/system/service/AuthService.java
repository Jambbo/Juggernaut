package com.example.system.service;

import com.example.system.web.dto.auth.JwtRequest;
import com.example.system.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
