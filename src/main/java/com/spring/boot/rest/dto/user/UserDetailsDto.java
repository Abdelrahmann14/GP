package com.spring.boot.rest.dto.user;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDetailsDto {
    private Long id;
    @NotBlank(message = "Invalid.Age")
    private String age;
    @NotBlank(message = "Invalid.Phone")
    @Size(min = 11, max = 11 ,message = "Invalid.PhoneNull")
    private String phoneNumber;
    @NotBlank(message = "Invalid.Address")
    private String address;

    private Long userId;
}
