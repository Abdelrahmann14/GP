package com.spring.boot.rest.service.contactInfo;

import com.spring.boot.rest.exceptions.customExceptions.IdException;
import com.spring.boot.rest.dto.contactInfo.ContactInfoDto;

public interface ContactInfoService {
    void saveContactInfo(ContactInfoDto contactInfoDto)throws IdException;
}
