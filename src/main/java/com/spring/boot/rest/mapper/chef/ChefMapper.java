package com.spring.boot.rest.mapper.chef;

import com.spring.boot.rest.dto.chef.ChefDto;
import com.spring.boot.rest.model.chef.Chef;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChefMapper {
    ChefMapper chefMapper = Mappers.getMapper(ChefMapper.class);
    ChefDto toChefDto(Chef chef);
    List<ChefDto> toListChefDto(List<Chef> chefs);

}
