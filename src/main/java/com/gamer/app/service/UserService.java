package com.gamer.app.service;

import com.gamer.app.exception.BadRequestException;
import com.gamer.app.exception.ResourceNotFoundException;
import com.gamer.app.model.Dto.auth.response.UserResponseDTO;
import com.gamer.app.model.Dto.user.request.ChangePasswordRequest;
import com.gamer.app.model.Dto.user.request.UpdateProfileRequest;
import com.gamer.app.model.entity.User;
import com.gamer.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO updateProfile(Long userId, UpdateProfileRequest request) {

        log.info("Actualizando perfil del usuario con id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        user.setUsername(request.getUsername());
        userRepository.save(user);

        log.info("Perfil actualizado exitosamente para el usuario: {}", user.getEmail());

        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername()
        );
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {

        log.info("Intentando cambiar contraseña para el usuario con id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("La contraseña actual es incorrecta");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        log.info("Contraseña actualizada exitosamente para el usuario: {}", user.getEmail());
    }
}

