package com.spring.boot.rest.model.category;
import com.spring.boot.rest.model.product.Product;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50,unique = true)
    private String name;
    @Column(nullable = false, length = 50)
    private String logo;
    @Column(nullable = false, length = 50)
    private String flag;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL) //any edit on catgory done on products too
    private List<Product> products;
}

