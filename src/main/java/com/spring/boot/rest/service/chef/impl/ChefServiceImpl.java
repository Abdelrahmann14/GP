package com.spring.boot.rest.service.chef.impl;
import com.spring.boot.rest.exceptions.customExceptions.ResourceFoundException;
import com.spring.boot.rest.dto.chef.ChefDto;
import com.spring.boot.rest.mapper.chef.ChefMapper;
import com.spring.boot.rest.model.chef.Chef;
import com.spring.boot.rest.repo.chef.ChefRepo;
import com.spring.boot.rest.service.chef.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefServiceImpl implements ChefService {
    @Autowired
    private ChefRepo chefRepo;
    @Override
    public List<ChefDto> getAllChefs() {
        List<Chef> chefs = chefRepo.findAllByOrderByIdAsc();
        if (chefs.isEmpty()) {
            throw new ResourceFoundException("Chefs.Not.Found");
        }
        return ChefMapper.chefMapper.toListChefDto(chefs);
    }

}
