package com.spring.boot.rest.controller.category;

import com.spring.boot.rest.exceptions.customExceptions.IdException;
import com.spring.boot.rest.exceptions.customExceptions.IdNullException;
import com.spring.boot.rest.exceptions.customExceptions.ResourceFoundException;
import com.spring.boot.rest.dto.category.CategoryDto;

import com.spring.boot.rest.service.category.CategoryService;
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
@RequestMapping("/Category")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin("http://localhost:4200")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getAllCategory")
    public ResponseEntity<List<CategoryDto>>getALlCategory() throws ResourceFoundException, SystemException {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/creat-Category")
    ResponseEntity<Void> creatCategory(@RequestBody @Valid CategoryDto categoryDto)throws IdException {
        categoryService.saveCategory(categoryDto);
        return ResponseEntity.created(URI.create("/creat-Category")).build();
    }

    @PostMapping("/creat-Categories")
    ResponseEntity<Void>  creatCategories(@RequestBody  List<@Valid CategoryDto> categoryDto)throws IdException {
        categoryService.saveCategories(categoryDto);
        return ResponseEntity.created(URI.create("/creat-Categories")).build();
    }
    @PutMapping("/update-Category")
    ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto) throws IdNullException {
       return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
    }
    @PutMapping("/update-Categories")
    ResponseEntity<List<CategoryDto>> updateCategories(@RequestBody  List<@Valid CategoryDto> categoryDto) throws IdNullException {
        return ResponseEntity.ok(categoryService.updateCategories(categoryDto));
    }
    @DeleteMapping("/delete-Category")
    ResponseEntity<Void> deleteCategory(@RequestBody CategoryDto categoryDto) throws IdNullException {
        categoryService.removeCategoryById(categoryDto.getId());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete-Categories")
    ResponseEntity<Void> deleteCategories(@RequestBody List<CategoryDto> categoryDtos) throws IdNullException {
        categoryService.removeCategoriesByIds(categoryDtos);
        return ResponseEntity.ok().build();
    }




}
