package com.spring.boot.rest.controller.product;

import com.spring.boot.rest.exceptions.customExceptions.IdException;
import com.spring.boot.rest.exceptions.customExceptions.IdNullException;
import com.spring.boot.rest.dto.Product.ProductDto;
import com.spring.boot.rest.service.product.ProductService;
import com.spring.boot.rest.vm.product.ProductResponseVm;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/Product")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin("http://localhost:4200")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getAllProductsByCategoryId/{categoryId}/page/{pageNo}/size/{pageSize}")
    public ResponseEntity<ProductResponseVm> getAllProductsByCategoryId(@PathVariable Long categoryId,@PathVariable int pageSize,@PathVariable int pageNo) throws SystemException {
        return ResponseEntity.ok(productService.getAllProductsByCategoryId(categoryId, pageSize, pageNo));
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
//    @GetMapping("/getAllProducts/page/{pageNo}/size/{pageSize}")
    @GetMapping("/getAllProducts")
    public ResponseEntity< List<ProductDto>/*ProductResponseVm*/> getAllProducts(/*@PathVariable int pageSize,@PathVariable int pageNo*/) throws SystemException {
        return ResponseEntity.ok(productService.getAllProducts(/*pageNo,pageSize*/));
    }
  //  @PreAuthorize("hasRole('USER')")
    @PostMapping("/creat-Product")
    public ResponseEntity<Void> creatProduct(@RequestBody @Valid ProductDto productDto) throws IdException, IdNullException {
        productService.saveProduct(productDto);
        return ResponseEntity.created(URI.create("/Product/creat-Product")).build();
    }
    @PostMapping("/creat-Products")
    public ResponseEntity<Void> creatProducts(@RequestBody List<@Valid ProductDto> productDto) throws IdException, IdNullException {
        productService.saveProducts(productDto);
        return ResponseEntity.created(URI.create("/Product/creat-Products")).build();
    }

    @PutMapping("/update-Product")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductDto productDto) throws IdNullException {
        return ResponseEntity.ok(productService.updateProduct(productDto));
    }

    @PutMapping("/update-Products")
    public ResponseEntity<List<ProductDto>> updateProducts(@RequestBody List<@Valid ProductDto> productDto) throws IdNullException {
        return ResponseEntity.ok(productService.updateProducts(productDto));
    }

    @DeleteMapping("/delete-Product")
    public ResponseEntity<Void> deleteProduct(@RequestBody ProductDto productDto) throws IdNullException {
        productService.removeProductById(productDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-Products")
    public ResponseEntity<Void> deleteProducts(@RequestBody List<ProductDto> productDtos) throws IdNullException {
        productService.removeProductsByIds(productDtos);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getAllProductsByName/{name}/page/{pageNo}/size/{pageSize}")
    public ResponseEntity<ProductResponseVm> getAllProductByName(@PathVariable String name, @PathVariable int pageNo, @PathVariable int pageSize) throws SystemException {
        return ResponseEntity.ok(productService.getProductByName(name,pageNo,pageSize));
    }


}
