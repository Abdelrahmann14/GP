package com.spring.boot.rest.service.chef;
import com.spring.boot.rest.dto.chef.ChefDto;
import java.util.List;

public interface ChefService {
    List<ChefDto> getAllChefs();
}
