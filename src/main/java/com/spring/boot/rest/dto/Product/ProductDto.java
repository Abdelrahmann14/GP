package com.spring.boot.rest.dto.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private Long id;
@NotBlank (message = "Invalid.Name")
@Size(min = 5, max = 20,message = "Invalid.NameSize")
    private String name;
@NotBlank (message = "Invalid.ImgPath")
    private String imagePath;
@NotBlank (message = "Invalid.Description")
@Size(min = 10, max = 50,message = "Invalid.DescriptionSize")
private String description;
@PositiveOrZero(message = "Invalid.PositiveOrZero")
    private Double price;
    private Long categoryId;
    private String categoryName;
}
