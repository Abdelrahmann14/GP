package com.spring.boot.rest.service.contactInfo.impl;
import com.spring.boot.rest.exceptions.customExceptions.IdException;
import com.spring.boot.rest.dto.contactInfo.ContactInfoDto;
import com.spring.boot.rest.mapper.contactInfo.ContactInfoMapper;
import com.spring.boot.rest.repo.contactInfo.ContactInfoRepo;
import com.spring.boot.rest.service.contactInfo.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ContactInfoServiceImpl implements ContactInfoService {
  @Autowired
  private ContactInfoRepo contactInfoRepo;

    @Override
    public void saveContactInfo(ContactInfoDto contactInfoDto)throws IdException {
            if (contactInfoDto.getId()!=null){
                throw new IdException("ErrorContact.Id.Notnull");
            }
            contactInfoRepo.save(ContactInfoMapper.contactInfoMapper.toContactInfo(contactInfoDto));
    }
}
