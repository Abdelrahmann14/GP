package com.spring.boot.rest.model.chef;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "chefs")
@Data
public class Chef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String spec;
    @Column(nullable = false)
    private String logoPath;
    @Column(nullable = false)
    private String faceLink;
    private String tweLink;
    private String instaLink;
}
