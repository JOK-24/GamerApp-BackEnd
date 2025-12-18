package com.gamer.app.model.Dto.user.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    private String username;
}
