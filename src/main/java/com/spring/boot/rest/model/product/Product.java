package com.spring.boot.rest.model.product;
import com.spring.boot.rest.model.category.Category;
import com.spring.boot.rest.model.order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50,unique = true)
    private String name;
    @Column(nullable = false, length = 50)
    private String imagePath;
    @Column(nullable = false, length = 1000)
    private String description;
    @Column(nullable = false)
    private Double price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
@ManyToMany(mappedBy = "products")
    private List<Order> orders;


}


