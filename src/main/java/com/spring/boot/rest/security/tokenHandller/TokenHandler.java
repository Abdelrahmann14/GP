package com.spring.boot.rest.security.tokenHandller;

import com.spring.boot.rest.dto.user.UserSignUpDto;
import com.spring.boot.rest.exceptions.customExceptions.TokenExceptions;
import com.spring.boot.rest.helper.TokenHelper;
import com.spring.boot.rest.model.user.Role;
import com.spring.boot.rest.service.user.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenHandler {

    private final Key key;
    private final JwtParser jwtParser;
    private final Duration time;

    @Autowired
    private UserService userService;

    public TokenHandler(TokenHelper tokenHelper) {
        this.time = tokenHelper.getTime();

        this.key = Keys.hmacShaKeyFor(
                tokenHelper.getSecretKey().getBytes(StandardCharsets.UTF_8)
        );

        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public String createToken(UserSignUpDto userSignUpDto) {

        Date issueDate = new Date();
        Date expiryDate = Date.from(issueDate.toInstant().plus(time));

        return Jwts.builder()
                .setSubject(userSignUpDto.getUsername())
                .setIssuedAt(issueDate)
                .setExpiration(expiryDate)
                .claim("roles",
                        userSignUpDto.getRoles()
                                .stream()
                                .map(Role::getRoleName)
                                .collect(Collectors.toList())
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public UserSignUpDto validateToken(String token)
            throws TokenExceptions, SystemException {

        Claims claims;

        try {
            claims = jwtParser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExceptions("Token.Expired");
        } catch (JwtException e) {
            throw new TokenExceptions("Token.Invalid");
        }

        String username = claims.getSubject();

        UserSignUpDto user = userService.getUserByName(username);

        if (user == null) {
            throw new TokenExceptions("Token.NotExists");
        }

        List<String> roleNames = claims.get("roles", List.class);

        List<Role> roles = roleNames.stream()
                .map(roleName -> {
                    Role role = new Role();
                    role.setRoleName(roleName);
                    return role;
                })
                .collect(Collectors.toList());

        user.setRoles(roles);

        return user;
    }
}
