package com.spring.boot.rest.repo.product;
import com.spring.boot.rest.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

//    @Query("SELECT p.id, p.name, p.imagePath, p.description, p.price, p.category.id FROM Product p WHERE p.name LIKE %:nameIn%")
//    List<Object[]> searchProductsByName(@Param("nameIn") String name);
    Page<Product> findByCategoryIdOrderByNameAsc(Long categoryId, Pageable pageable);
    List<Product> findAllByOrderByNameAsc();
    Page<Product> findByNameContainingIgnoreCaseOrderByNameAsc(String name, Pageable pageable);
}
