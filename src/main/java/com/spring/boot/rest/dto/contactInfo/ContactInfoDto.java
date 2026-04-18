package com.spring.boot.rest.dto.contactInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactInfoDto {
    private Long id;
    @NotBlank(message = "Specification is mandatory")
    private String name;
    @NotBlank(message = "Specification is mandatory")
    @Email
    private String email;
    @NotBlank(message = "Specification is mandatory")
    private String message;
    @NotBlank(message = "Specification is mandatory")
    private String subject;
    private Long userId;
}
