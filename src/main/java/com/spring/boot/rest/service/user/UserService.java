package com.spring.boot.rest.service.user;

import com.spring.boot.rest.dto.user.UserSignUpDto;
import com.spring.boot.rest.exceptions.customExceptions.ResourceFoundException;
import jakarta.transaction.SystemException;

public interface UserService {
    UserSignUpDto getUserByName(String userName) throws SystemException;
    boolean addUser(UserSignUpDto userSignUpDto) throws ResourceFoundException;

}
