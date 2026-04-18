package com.spring.boot.rest.repo.category;

import com.spring.boot.rest.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    List<Category> findAllByOrderByNameAsc();
}
