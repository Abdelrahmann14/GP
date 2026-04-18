package com.spring.boot.rest.service.auth.impl;

import com.spring.boot.rest.dto.user.UserLoginDto;
import com.spring.boot.rest.dto.user.UserSignUpDto;
import com.spring.boot.rest.exceptions.customExceptions.ResourceFoundException;
import com.spring.boot.rest.service.auth.AuthService;
import com.spring.boot.rest.service.user.UserService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserSignUpDto userSignUpDto) {
            userService.addUser(userSignUpDto);
    }

    @Override
    public UserSignUpDto login(UserLoginDto userLoginDto) throws ResourceFoundException, SystemException {
        UserSignUpDto existUser = userService.getUserByName(userLoginDto.getUsername());
        if(Objects.isNull(existUser)){
            throw new ResourceFoundException("User.NotExists");
        }
        if (!passwordEncoder.matches(userLoginDto.getPassword(), existUser.getPassword())) {
            throw new ResourceFoundException("Invalid.NotMatchesPassword");
        }
            return existUser;
    }
}
