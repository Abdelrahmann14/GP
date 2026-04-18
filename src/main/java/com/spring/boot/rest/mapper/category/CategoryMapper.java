package com.spring.boot.rest.mapper.category;
import com.spring.boot.rest.dto.category.CategoryDto;
import com.spring.boot.rest.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;
@Mapper
public interface CategoryMapper  {
    CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    Category toCategory (CategoryDto categoryDto);
    CategoryDto toCategoryDto (Category category);
    List<Category> toListCategory (List<CategoryDto> categoryDto);
    List<CategoryDto> toListCategoryDto (List<Category> category);

}
