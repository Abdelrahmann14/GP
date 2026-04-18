package com.spring.boot.rest.dto.user;
import com.spring.boot.rest.model.user.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public class UserSignUpDto {
    private Long id;
    @NotBlank(message = "Invalid.UserNameNull")
    @Size(min = 13, max = 50,message = "Invalid.UserNameSize")
    @Email(message = "Invalid.UserNameEmail")
    private String username;
    @NotBlank(message = "Invalid.Validation.Password")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,}$",
            message = "Invalid.Validation.ErrorPassword"
    )
    private String password;
    @Valid
    private UserDetailsDto userDetails;
    private List<Role> roles;
}

