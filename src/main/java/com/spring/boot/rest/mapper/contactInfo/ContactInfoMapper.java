package com.spring.boot.rest.mapper.contactInfo;
import com.spring.boot.rest.dto.contactInfo.ContactInfoDto;
import com.spring.boot.rest.model.contactInfo.ContactInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ContactInfoMapper {
    ContactInfoMapper contactInfoMapper = Mappers.getMapper(ContactInfoMapper.class);
    ContactInfo toContactInfo (ContactInfoDto contactInfoDto);

}
