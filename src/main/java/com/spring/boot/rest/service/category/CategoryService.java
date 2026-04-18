package com.spring.boot.rest.service.category;
import com.spring.boot.rest.exceptions.customExceptions.IdException;
import com.spring.boot.rest.exceptions.customExceptions.IdNullException;
import com.spring.boot.rest.exceptions.customExceptions.ResourceFoundException;
import com.spring.boot.rest.dto.category.CategoryDto;
import jakarta.transaction.SystemException;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories() throws ResourceFoundException, SystemException;

    void saveCategory(CategoryDto categoryDto) throws IdException;

    void saveCategories(List<CategoryDto> categoryDto) throws IdException;

    CategoryDto updateCategory( CategoryDto categoryDto)throws IdNullException;

    List<CategoryDto> updateCategories( List<CategoryDto> categoryDto)throws IdNullException;

     void removeCategoryById( Long id)throws IdNullException;

    void removeCategoriesByIds(List<CategoryDto> categoryDtos) throws IdNullException;
}

