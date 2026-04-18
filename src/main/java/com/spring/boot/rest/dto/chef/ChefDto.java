package com.spring.boot.rest.dto.chef;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class ChefDto {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Specification is mandatory")
    private String spec;
    private String logoPath;
    private String faceLink;
    private String tweLink;
    private String instaLink;
}
