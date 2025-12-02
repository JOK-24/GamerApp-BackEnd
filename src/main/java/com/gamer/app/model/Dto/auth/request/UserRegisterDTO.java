package com.gamer.app.model.Dto.auth.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private String email;
    private String username;
    private String password;
}
