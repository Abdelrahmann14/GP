package com.spring.boot.rest.service.product;

import com.spring.boot.rest.exceptions.customExceptions.IdException;
import com.spring.boot.rest.exceptions.customExceptions.IdNullException;
import com.spring.boot.rest.exceptions.customExceptions.ResourceFoundException;
import com.spring.boot.rest.dto.Product.ProductDto;
import com.spring.boot.rest.vm.product.ProductResponseVm;
import jakarta.transaction.SystemException;

import java.util.List;

public interface ProductService {
    List<ProductDto>/*ProductResponseVm*/ getAllProducts(/*int pageSize,int pageNo*/) throws SystemException;

    ProductResponseVm getAllProductsByCategoryId(Long categoryId,int pageSize,int pageNo)throws SystemException;

    void saveProducts(List<ProductDto> productDto) throws IdException,IdNullException;

    void saveProduct( ProductDto productDto) throws IdException,IdNullException;

    ProductDto updateProduct( ProductDto productDto) throws  IdNullException, ResourceFoundException;

    List<ProductDto> updateProducts( List<ProductDto> productDtos) throws IdNullException, ResourceFoundException;

    void removeProductById(Long id) throws IdNullException, ResourceFoundException;

    void removeProductsByIds(List<ProductDto> productDtos)  throws IdNullException, ResourceFoundException;

    ProductResponseVm getProductByName(String name, int pageSize, int pageNo) throws SystemException;

}
