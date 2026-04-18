package com.spring.boot.rest.model.user;

public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER"),
    ;
    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleName() {
        return roleName;
    }
}
