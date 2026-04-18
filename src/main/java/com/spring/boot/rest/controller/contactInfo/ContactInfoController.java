package com.spring.boot.rest.controller.contactInfo;
import com.spring.boot.rest.exceptions.customExceptions.IdException;
import com.spring.boot.rest.dto.contactInfo.ContactInfoDto;
import com.spring.boot.rest.service.contactInfo.ContactInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/ContactInfo")
public class ContactInfoController {
@Autowired
    private ContactInfoService contactInfoService;
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/createContactInfo")
    ResponseEntity<Void> createContactInfo(@RequestBody @Valid ContactInfoDto contactInfoDto)throws IdException {
        contactInfoService.saveContactInfo(contactInfoDto);
        return ResponseEntity.created(URI.create("/ContactInfo/createContactInfo")).build();
    }

}
