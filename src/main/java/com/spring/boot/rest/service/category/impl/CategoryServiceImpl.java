package com.spring.boot.rest.service.category.impl;

import com.spring.boot.rest.exceptions.customExceptions.IdException;
import com.spring.boot.rest.exceptions.customExceptions.IdNullException;
import com.spring.boot.rest.exceptions.customExceptions.ResourceFoundException;
import com.spring.boot.rest.dto.category.CategoryDto;
import com.spring.boot.rest.mapper.category.CategoryMapper;
import com.spring.boot.rest.model.category.Category;
import com.spring.boot.rest.repo.category.CategoryRepo;
import com.spring.boot.rest.service.category.CategoryService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
@Autowired
private CategoryRepo categoryRepo;
    @Override
    public List<CategoryDto> getAllCategories() throws ResourceFoundException, SystemException {

        List<Category> categories = categoryRepo.findAllByOrderByNameAsc();
             if ( categories.isEmpty()){
                 throw new ResourceFoundException("Category.Not.Found");
             }
             return CategoryMapper.categoryMapper.toListCategoryDto(categories);

    }


    @Override
    public void saveCategory(CategoryDto categoryDto) throws IdException {
        if (categoryDto.getId()!=null){
            throw new IdException("Error.Id.Notnull");
        }
        categoryRepo.save(CategoryMapper.categoryMapper.toCategory(categoryDto));
    }

    @Override
    public void saveCategories(List<CategoryDto> categoryDtos) throws IdException {
        for (CategoryDto categoryDto : categoryDtos) {
            if (categoryDto.getId() != null) {
                throw new IdException("Error.Id.Notnull");
            }
        }
            categoryRepo.saveAll(CategoryMapper.categoryMapper.toListCategory(categoryDtos));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) throws IdNullException {
        if (categoryDto.getId()==null){
            throw new IdNullException("Error.Id.Null");
        }
        if (!categoryRepo.existsById(categoryDto.getId())){
            throw new ResourceFoundException("CategoryId.Not.Founded");
        }
        Category categorySaved = categoryRepo.save(CategoryMapper.categoryMapper.toCategory(categoryDto));
        return CategoryMapper.categoryMapper.toCategoryDto(categorySaved);
    }
    @Override
    public List<CategoryDto> updateCategories(List<CategoryDto> categoryDtos) throws IdNullException {
        for (CategoryDto categoryDto : categoryDtos) {
            if (categoryDto.getId()==null){
                throw new IdNullException("Error.Id.Null");
            }
            if (!categoryRepo.existsById(categoryDto.getId())){
                throw new ResourceFoundException("CategoryId.Not.Founded");
            }
        }
       List<Category> categoriesSaved = categoryRepo.saveAll(CategoryMapper.categoryMapper.toListCategory(categoryDtos));
        return CategoryMapper.categoryMapper.toListCategoryDto(categoriesSaved);
    }

    @Override
    public void removeCategoryById(Long id) throws ResourceFoundException,IdNullException {
        if (id==null){
            throw new IdNullException("Error.Id.Null");
        }
        else if (!categoryRepo.existsById(id)){
            throw new ResourceFoundException("Category.Not.Found");
        }
        else {
        categoryRepo.deleteById(id);
    }
    }

    @Override
    public void removeCategoriesByIds(List<CategoryDto> categoryDtos) throws IdNullException, ResourceFoundException {
        for (CategoryDto dto : categoryDtos) {
            Long id = dto.getId();

            if (id == null) {
                throw new IdNullException("Error.Id.Null");
            }

            if (!categoryRepo.existsById(id)) {
                throw new ResourceFoundException("Error.Id.Not.Found");
            }

            categoryRepo.deleteById(id);
        }
    }
    private Pageable getPageable(int pageNo, int pageSize) throws SystemException {
        if (pageNo<=0)
        {  // TODO  HANDLE BUNDLE MESSAGE
            throw new SystemException("Invalid.PageNo");
        }
        return PageRequest.of(pageNo-1, pageSize);
    }
}

