package com.gamer.app.controller;

import com.gamer.app.model.Dto.auth.response.UserResponseDTO;
import com.gamer.app.model.Dto.common.ApiResponse;
import com.gamer.app.model.Dto.user.request.ChangePasswordRequest;
import com.gamer.app.model.Dto.user.request.UpdateProfileRequest;
import com.gamer.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/{id}/profile")
    public ApiResponse<UserResponseDTO> updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequest request
    ) {
        UserResponseDTO user = userService.updateProfile(id, request);

        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("Perfil actualizado correctamente")
                .data(user)
                .build();
    }

    @PutMapping("/{id}/password")
    public ApiResponse<Void> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request
    ) {
        userService.changePassword(id, request);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Contraseña actualizada correctamente")
                .build();
    }
}
