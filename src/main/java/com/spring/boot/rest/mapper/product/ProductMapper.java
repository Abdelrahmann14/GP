package com.spring.boot.rest.mapper.product;
import com.spring.boot.rest.dto.Product.ProductDto;
import com.spring.boot.rest.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;
@Mapper
public interface ProductMapper {
    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
 ProductDto toProductDto (Product product);
 Product toProduct (ProductDto productDto);
 List<ProductDto> toListProductDto (List<Product> product);
 List<Product> toListProduct (List<ProductDto> productDto);

}
