package com.spring.boot.rest.controller.auth;

import com.spring.boot.rest.dto.user.UserLoginDto;
import com.spring.boot.rest.dto.user.UserSignUpDto;
import com.spring.boot.rest.security.tokenHandller.TokenHandler;
import com.spring.boot.rest.security.tokenHandller.TokenResponse;
import com.spring.boot.rest.service.auth.AuthService;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private TokenHandler tokenHandler;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserSignUpDto userSignUpDto) throws SystemException {
        authService.signUp(userSignUpDto);
        return ResponseEntity.created(URI.create("/auth/sign-up")).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid UserLoginDto userLoginDto) throws SystemException {
        UserSignUpDto acceptedUser = authService.login(userLoginDto);
        String token = tokenHandler.createToken(acceptedUser);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
