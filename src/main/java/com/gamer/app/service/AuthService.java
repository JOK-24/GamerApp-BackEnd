package com.gamer.app.service;


import com.gamer.app.exception.BadRequestException;
import com.gamer.app.model.Dto.auth.request.UserLoginDTO;
import com.gamer.app.model.Dto.auth.request.UserRegisterDTO;
import com.gamer.app.model.Dto.auth.response.UserResponseDTO;
import com.gamer.app.model.entity.User;
import com.gamer.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO register(UserRegisterDTO dto) {
        log.info("Registrando nuevo usuario: {}", dto.getEmail());

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BadRequestException("El username ya existe");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);

        log.info("Usuario registrado exitosamente: {}", user.getEmail());

        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername()
        );
    }

    @Transactional
    public UserResponseDTO login(UserLoginDTO dto) {
        log.info("Intentando login para: {}", dto.getEmail());

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BadRequestException("Credenciales inválidas"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadRequestException("Credenciales inválidas");
        }

        log.info("Login exitoso para: {}", user.getEmail());

        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername()
        );
    }
}
