package com.spring.boot.rest.vm.product;

import com.spring.boot.rest.dto.Product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class ProductResponseVm {
    private List<ProductDto> products;
    private Long totalProducts;

    }

