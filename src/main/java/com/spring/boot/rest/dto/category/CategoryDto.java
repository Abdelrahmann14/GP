package com.spring.boot.rest.dto.category;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CategoryDto {
    private Long id;
    @NotBlank (message = "Invalid.Name")
    @Size(min = 5, max = 30,message = "Invalid.NameSize")
    private String name;
    @NotBlank (message = "Invalid.Logo")
    private String logo;
    @NotBlank (message = "Invalid.Flag")
    private String flag;
}

