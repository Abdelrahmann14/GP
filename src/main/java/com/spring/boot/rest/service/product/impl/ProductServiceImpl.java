package com.spring.boot.rest.service.product.impl;
import com.spring.boot.rest.exceptions.customExceptions.IdException;
import com.spring.boot.rest.exceptions.customExceptions.IdNullException;
import com.spring.boot.rest.exceptions.customExceptions.ResourceFoundException;
import com.spring.boot.rest.dto.Product.ProductDto;
import com.spring.boot.rest.mapper.product.ProductMapper;
import com.spring.boot.rest.model.category.Category;
import com.spring.boot.rest.model.product.Product;
import com.spring.boot.rest.repo.category.CategoryRepo;
import com.spring.boot.rest.repo.product.ProductRepo;
import com.spring.boot.rest.service.product.ProductService;
import com.spring.boot.rest.vm.product.ProductResponseVm;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public ProductResponseVm getAllProductsByCategoryId(Long categoryId, int pageSize, int pageNo) throws SystemException {
        Page<Product> products= productRepo.findByCategoryIdOrderByNameAsc(categoryId,getPageable(pageNo,pageSize));
        return getVm(products);
    }
    @Override
    public List<ProductDto>/*ProductResponseVm*/ getAllProducts(/*int pageSize, int pageNo*/) throws SystemException {
        List<Product> products = productRepo.findAllByOrderByNameAsc(/*getPageable(pageNo, pageSize)*/);
        if (products./*getContent().*/isEmpty()) {
            throw new ResourceFoundException("Products.Not.Found");
        }
        List<ProductDto> productDtos = ProductMapper.productMapper.toListProductDto(products/*.getContent()*/);
        for (int i = 0; i < productDtos.size(); i++) {
            ProductDto dto = productDtos.get(i);
            String categoryName = products./*getContent().*/get(i).getCategory().getName();
            dto.setCategoryName(categoryName);
        }
        return productDtos;
    }

    @Override
    public void saveProducts(List<ProductDto> productDtos) throws IdNullException, IdException {
        for (ProductDto productDto : productDtos) {
            if (productDto.getId() != null) {
                throw new IdException("Error.Id.Notnull");
            }
            else if(productDto.getCategoryId() == null){
                throw new IdNullException("Error.Id.Notnull");
            }
        }
        getListOfProducts(productDtos);
    }
    @Override
    public void saveProduct(ProductDto productDto) throws IdException,IdNullException {
        if(productDto.getId()!=null){
            throw  new IdException("Error.Id.Notnull");
        }
        else if(productDto.getCategoryId()==null){
            throw  new IdNullException("ErrorCategory.Id.Null");
        }
        Product product = ProductMapper.productMapper.toProduct(productDto);
        Category category = categoryRepo.findById(productDto.getCategoryId()).orElseThrow(() -> new ResourceFoundException("CategoryId.Not.Founded"));
        product.setCategory(category);
        productRepo.save(product);
    }


    @Override
    public ProductDto updateProduct(ProductDto productDto) throws IdNullException, ResourceFoundException {
        if(productDto.getId()==null){
            throw new IdNullException("Error.Id.Null");
        }
        if (!productRepo.existsById(productDto.getId())) {
            throw new ResourceFoundException("Error.Id.Not.Found");
        }
        Product product = ProductMapper.productMapper.toProduct(productDto);
        Category category = categoryRepo.findById(productDto.getCategoryId()).orElseThrow(() -> new ResourceFoundException("CategoryId.Not.Founded"));
        product.setCategory(category);
        ProductDto updatedProductDto = ProductMapper.productMapper.toProductDto(productRepo.save(product));
        updatedProductDto.setCategoryId(category.getId());
        return updatedProductDto;
    }


    @Override
    public List<ProductDto> updateProducts(List<ProductDto> productDtos) throws IdNullException, ResourceFoundException {
        for (ProductDto productDto : productDtos) {
            if (productDto.getId() == null) {
                throw new IdNullException("Error.Id.Null");
            }
            if (!productRepo.existsById(productDto.getId())) {
                throw new ResourceFoundException("Error.Id.Not.Found");
            }
        }
            getListOfProducts(productDtos);
        return productDtos;
    }

    @Override
    public void removeProductById(Long id)  throws IdNullException, ResourceFoundException {
        if(id==null){
            throw new IdNullException("Error.Id.Null");
        }
        else if (!productRepo.existsById(id)) {
            throw new ResourceFoundException("Error.Id.Not.Found");
        }
        else  {
            productRepo.deleteById(id);
        }

    }

    @Override
    public void removeProductsByIds(List<ProductDto> productDtos) throws IdNullException, ResourceFoundException {
        for (ProductDto dto : productDtos) {
            Long id = dto.getId();

            if (id == null) {
                throw new IdNullException("Error.Id.Null");
            }

            if (!productRepo.existsById(id)) {
                throw new ResourceFoundException("Error.Id.Not.Found");
            }

            productRepo.deleteById(id);
        }
    }
    @Override
    public ProductResponseVm getProductByName(String name,int pageNo,int pageSize) throws SystemException {
        Page<Product> products =productRepo.findByNameContainingIgnoreCaseOrderByNameAsc(name, getPageable(pageNo,pageSize));
return getVm(products);
    }


    /*local functions */
    private void getListOfProducts(List<ProductDto> productDtos) {
        List<Product> products = productDtos.stream().map(productDto -> {
            Product product = ProductMapper.productMapper.toProduct(productDto);

            Category category = categoryRepo.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ResourceFoundException("CategoryId.Not.Founded"));

            product.setCategory(category);
            return product;
        }).toList();

        productRepo.saveAll(products);
    }
    private Pageable getPageable(int pageNo, int pageSize) throws SystemException{
        if (pageNo<=0)
        {  // TODO  HANDLE BUNDLE MESSAGE
            throw new SystemException("Invalid.PageNo");
        }
        return PageRequest.of(pageNo-1, pageSize);
    }

    private ProductResponseVm getVm(Page<Product> products) {
        if (products == null || products.getContent().isEmpty()) {
            throw new ResourceFoundException("Products.Not.Found");
        }
        return new ProductResponseVm(
                products.getContent().stream()
                        .map(ProductMapper.productMapper::toProductDto)
                        .toList(),
                products.getTotalElements()
        );
    }


}


