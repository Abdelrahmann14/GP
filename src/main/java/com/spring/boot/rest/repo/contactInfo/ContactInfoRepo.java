package com.spring.boot.rest.repo.contactInfo;

import com.spring.boot.rest.model.contactInfo.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactInfoRepo extends JpaRepository<ContactInfo, Long> {
}
