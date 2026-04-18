package com.spring.boot.rest.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDto {
    @NotBlank(message = "Invalid.UserNameNull")
    private String username;

    @NotBlank(message = "Invalid.Validation.Password")
    private String password;
}
