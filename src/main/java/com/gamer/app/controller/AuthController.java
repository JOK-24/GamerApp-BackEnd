package com.gamer.app.controller;

import com.gamer.app.model.Dto.auth.request.UserLoginDTO;
import com.gamer.app.model.Dto.auth.request.UserRegisterDTO;
import com.gamer.app.model.Dto.auth.response.UserResponseDTO;
import com.gamer.app.model.Dto.common.ApiResponse;
import com.gamer.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@RequestBody UserRegisterDTO dto) {
        UserResponseDTO user = authService.register(dto);
        ApiResponse<UserResponseDTO> response = ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("Usuario registrado exitosamente")
                .data(user)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDTO>> login(@RequestBody UserLoginDTO dto) {
        UserResponseDTO user = authService.login(dto);
        ApiResponse<UserResponseDTO> response = ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("Login exitoso")
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }
}