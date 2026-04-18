package com.spring.boot.rest.service.auth;

import com.spring.boot.rest.dto.user.UserLoginDto;
import com.spring.boot.rest.dto.user.UserSignUpDto;
import jakarta.transaction.SystemException;

public interface AuthService {
    void signUp( UserSignUpDto userSignUpDto) throws SystemException ;

    UserSignUpDto login(UserLoginDto userLoginDto) throws SystemException;
}
