package com.spring.boot.rest.dto.user;
import com.spring.boot.rest.model.user.RoleEnum;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private String roleName;
}
