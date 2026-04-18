package com.spring.boot.rest.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long id;
    @NotBlank(message = "Invalid.Code")
    private String code;
    @PositiveOrZero(message ="Invalid.Price")
    private Double totalPrice;
    private Integer totalNumber;
    private Long userId;
    private List<Long> productIds;
}
