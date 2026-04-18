package com.spring.boot.rest.controller.chef;

import com.spring.boot.rest.exceptions.customExceptions.ResourceFoundException;
import com.spring.boot.rest.dto.chef.ChefDto;

import com.spring.boot.rest.service.chef.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chef")
public class ChefController {
    @Autowired
    private ChefService chefService;
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/getAllChefs")
    public ResponseEntity<List<ChefDto>> getAllChefs()throws ResourceFoundException {
        return ResponseEntity.ok(chefService.getAllChefs());
    }
}
