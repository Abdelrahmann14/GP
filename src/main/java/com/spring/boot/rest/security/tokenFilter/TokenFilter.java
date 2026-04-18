package com.spring.boot.rest.security.tokenFilter;
import com.spring.boot.rest.dto.user.UserSignUpDto;
import com.spring.boot.rest.security.tokenHandller.TokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenHandler tokenHandler;
    /* jwt filter done before system default filter */
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("authorization");  // take what type of auth from header of token
        if (Objects.isNull(token) || !token.startsWith("Bearer ")) {
            //if it false returns request with statues
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        // if auth true
        token = token.substring(7); // take toke without type of token to do operations
        UserSignUpDto userSignUpDto = tokenHandler.validateToken(token);
        if (Objects.isNull(userSignUpDto)) {
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        List<GrantedAuthority> roles=(userSignUpDto.getRoles().stream().map(roleDto -> new SimpleGrantedAuthority("ROLE_" + roleDto.getRoleName())).collect(Collectors.toList()));
// throws username,password and role to the next filter (spring user filter)
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new  UsernamePasswordAuthenticationToken(userSignUpDto, userSignUpDto.getPassword(),roles);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request,response); // continue filters after this jwtFilter
    }

    // filter will operate on which api ?
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path=request.getServletPath();
        return path.startsWith("/auth");
    }




}
